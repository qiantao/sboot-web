package com.qt.demo.controller;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.qt.demo.common.mysql.HKSource;
import com.qt.demo.common.mysql.MyBatitsDao;
import com.qt.demo.common.redis.RedisCache;
import com.qt.demo.manager.MyManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@RestController
@RequestMapping("/xocean")
@Slf4j
public class XoceanController {

    private MyManager myManager;
    private RedisCache redisCache;

    public XoceanController(MyManager myManager, RedisCache redisCache){
        this.myManager = myManager;
        this.redisCache = redisCache;
    }

    @GetMapping("/excel")
    public String parseExcel(@RequestParam("excelName") String excelName) throws Exception{
        String path =  System.getProperty("user.dir") +"/"+"file/";
        String filePath=path+excelName;
        Map<String, ArrayList> columnData = new HashMap<>();
        // 读取文件流
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Sheet sheet = null;
        Workbook workbook=null;
        if (excelName.contains(".xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } else if (excelName.contains(".xls")) {
            workbook = new HSSFWorkbook(fileInputStream);
        }else{
            System.out.println("不是excle文件");
        }
        sheet = workbook.getSheetAt(0);
        // 获取列数和行数
        int columnCount = sheet.getRow(0).getLastCellNum();
        int rowCount = sheet.getLastRowNum();

        // 遍历每一列
        for (int i = 0; i < columnCount; i++) {
            // 获取第一行的数据作为key
            Cell cell = sheet.getRow(0).getCell(i);
            String key = cell.getStringCellValue();
            ArrayList<Object> values = new ArrayList<>();// 除第一行以外其他行的数据
            // 遍历该列除第一行以外的每一行
            for (int j = 0; j < rowCount; j++) {
                cell = sheet.getRow(j + 1).getCell(i);
                // 如果单元格内的数据为空，输出空字符串，否则，添加到列表中
                if (cell != null) {
                    if (cell.getCellType() == CellType.BOOLEAN) {
                        values.add(j, String.valueOf(cell.getBooleanCellValue()));
                    } else if (cell.getCellType() == CellType.STRING) {
                        Charset charset = StandardCharsets.UTF_8;
                        values.add(j, new String(cell.getStringCellValue()));
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        short format = cell.getCellStyle().getDataFormat();
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date d = cell.getDateCellValue();
                            DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                            values.add(j, formater.format(d));
                        } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                            // 日期
                            DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                            values.add(j, formater.format(date));
                        } else if (format == 20 || format == 32) {
                            // 时间
                            DateFormat formater = new SimpleDateFormat("HH:mm");
                            Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                            values.add(j, formater.format(date));
                        } else {
                            DecimalFormat df = new DecimalFormat("0");
                            values.add(j, df.format(cell.getNumericCellValue()));
                        }
                    } else if (cell.getCellType() == CellType.BLANK) {
                        values.add(j, "");
                    }
                } else {
                    values.add(j, "");
                }
            }
            // 将该列的数据存储到字典中
            columnData.put(key, values);
        }
        workbook.close();
        return JSONUtil.toJsonStr(columnData);
    }
    @PostMapping("/post/json")
    public String postJson(@RequestBody Map<String,Object> map){
        return JSONUtil.toJsonStr(map);
    }


    @GetMapping("/get")
    public String get(@RequestParam Map<String,Object> map){
        return JSONUtil.toJsonStr(map);
    }

//    @PostMapping("/p")
    @RequestMapping("/p")
    public String l(@RequestBody Map<String,Object> map){
//        person.setTime(LocalDateTime.now());
//        person.getTime().getSecond()
//        Connection connection = MsqlDBConnection.getConnection();
        return JSONUtil.toJsonStr(map);
    }


    @PostMapping("/post/form-data")
    public String pFormData(@RequestParam Map<String,Object> map,
                            @RequestParam(value = "file1",required = false) MultipartFile file1,
                            @RequestParam(value = "file2",required = false) MultipartFile file2
                            ){
        if(file1!=null) {
            if (file1.isEmpty()) {
                return "Please select a file to upload";
            }

            try {
                String path =  System.getProperty("user.dir") +"/"+"file/";
                // Get the file and save it somewhere
                byte[] bytes = file1.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                String allPath = path +file1.getOriginalFilename();
//                String allPath = path + "data_file1_"+file1.getOriginalFilename();
                File f = new File(allPath);
                if(!f.getParentFile().exists()){
                    f.getParentFile().mkdirs();
                }
                if(!f.exists()){
                    f.createNewFile();
                }
                Files.write(bytes, f);

            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }

            map.put("file1",file1.getOriginalFilename());
        }
        if(file2!=null) {
            if (file2.isEmpty()) {
                return "Please select a file to upload";
            }

            try {
                String path =  System.getProperty("user.dir") +"/"+"file/";
                // Get the file and save it somewhere
                byte[] bytes = file2.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                String allPath = path + "data_file2_"+file2.getOriginalFilename();
                File f = new File(allPath);
                if(!f.getParentFile().exists()){
                    f.getParentFile().mkdirs();
                }
                if(!f.exists()){
                    f.createNewFile();
                }
                Files.write(bytes, f);

            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }

            map.put("file2",file2.getOriginalFilename());
        }

        return JSONUtil.toJsonStr(map);
    }

    @PostMapping("/post/urlencoded")
    public String pUrlencoded(@RequestParam Map<String,Object> map,
                              @RequestParam(value = "file1",required = false) MultipartFile file1,
                              @RequestParam(value = "file2",required = false) MultipartFile file2
                                ){
        if(file1!=null) {
            if (file1.isEmpty()) {
                return "Please select a file to upload";
            }

            try {
                String path =  System.getProperty("user.dir") +"/"+"file/";
                // Get the file and save it somewhere
                byte[] bytes = file1.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                String allPath = path + "urlencoded_file1_"+file1.getOriginalFilename();
                File f = new File(allPath);
                if(!f.getParentFile().exists()){
                    f.getParentFile().mkdirs();
                }
                if(!f.exists()){
                    f.createNewFile();
                }
                Files.write(bytes, f);

            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }

            map.put("file1",file1.getOriginalFilename());
        }
        if(file2!=null) {
            if (file2.isEmpty()) {
                return "Please select a file to upload";
            }

            try {
                String path =  System.getProperty("user.dir") +"/"+"file/";
                // Get the file and save it somewhere
                byte[] bytes = file2.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                String allPath = path + "urlencoded_file2_"+file2.getOriginalFilename();
                File f = new File(allPath);
                if(!f.getParentFile().exists()){
                    f.getParentFile().mkdirs();
                }
                if(!f.exists()){
                    f.createNewFile();
                }
                Files.write(bytes, f);

            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }

            map.put("file2",file2.getOriginalFilename());
        }

        return JSONUtil.toJsonStr(map);
    }

    @GetMapping("ttt")
    public String tt(@RequestParam("key") String key ){
        System.out.println(key);
        return key;
    }

    @PostMapping("a")
    public String a(@RequestParam("key") String key ){
        return key;
    }

    @GetMapping("getCon")
    public String addCon(){
//        List<String> strings = MyBatitsDao.queryColumnInfoByTableName(Lists.newArrayList("openapi_case_key"), "perfma_xocean");
        Connection con = HKSource.getCon();
        List<String> strings1 = MyBatitsDao.queryColumnInfoByTableName(con, Lists.newArrayList("openapi_case_key"), "perfma_xocean");
        return strings1.toString();
    }

}
