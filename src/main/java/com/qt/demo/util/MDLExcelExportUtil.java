package com.qt.demo.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Exceld导出工具类
 * @Author MuYang 
 * @Date 2022/6/22 11:27
 * @version: V1.0
 */
public class MDLExcelExportUtil {

    /**
     * Excel文件导出,导出的文件名默认为:headTitle+当前系统时间
     * @param listData 要导出的list数据
     * @param pojoClass 定义excel属性信息
     * @param headTitle Excel文件头信息
     * @param sheetName Excel文件sheet名称
     * @param response
     */
    public static void exportExcel(Collection<?> listData, Class<?> pojoClass, String headTitle, String sheetName, HttpServletResponse response) {
        String title = headTitle;
//        ExportParams params = new ExportParams(title, sheetName);
        ExportParams params = new ExportParams(null, sheetName);
        params.setHeight((short) 30);
        params.setStyle(ExcelExportMyStylerImpl.class);
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, pojoClass, listData);
            String fileName = headTitle;
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.setContentType("application/vnd.ms-excel;chartset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName + ".xls");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            ServletOutputStream out=response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹下所有文件
     * @param f
     */
    public static void delete(File f){
        if(f.isDirectory()){
            for (File file : f.listFiles()) {
                delete(file);
            }
            f.delete();
        }else{
            f.delete();
        }
    }

    public static String downloadProblem(Collection<?> listData,
                                       Class<?> pojoClass,
                                       List<OnesWorkFile> onesWorkFileList){
        String uk = UUID.randomUUID().toString();
        downloadExcel(listData,pojoClass,uk);
        onesWorkFileList.forEach(e->{
//            if(StringUtils.isNotBlank(e.getFilePath())) {
               // downloadFile(e.getFilePath(),e.getProblemId(),uk);
//            }
        });
        String zip = ZipUtils.zip(FileUtils.getTempPath(uk), FileUtils.getZipPath(uk));
        //压缩完后删除
        File f = new File(FileUtils.getTempPath(uk));
        delete(f);
        return zip;

    }

    /**
     * 下载文件
     * @param allFileName 文件名
     * @param problemId 问题id 用于作为文件夹
     * @param uk 此次下载的唯一id
     * @return
     */
    public static String downloadFile(String allFileName,Long problemId,String uk){
        FileOutputStream out = null;
        BufferedInputStream bis = null;
        try {
            File f = new File(allFileName);
            if(!f.exists()){
                return "";
            }
            int index = allFileName.lastIndexOf("/");
            String fileName = allFileName.substring(index+1);
            bis = new BufferedInputStream(new FileInputStream(f));
            byte[] bytes = new byte[bis.available()];
            bis.read(bytes);
            String targetFile = uk+"/"+problemId+"/"+fileName;
            out = getFileOut(targetFile);
            out.write(bytes);
            out.flush();
            out.close();
            return FileUtils.getTempPath()+targetFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(bis != null) bis.close();
                if(out != null) out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Excel文件下载到本地目录 默认为./temp/yyyy-MM-dd/
     * @param listData 要导出的list数据
     * @param pojoClass 定义excel属性信息
     */
    public static void downloadExcel(Collection<?> listData,
                                     Class<?> pojoClass,
                                     String uk
                                    ) {
        Workbook workbook = null;
        String fileName = "问题";//Excel文件名
        String sheetName = "迭代压测问题";//Excel文件sheet名称
        ExportParams params = new ExportParams(null, sheetName);
        params.setHeight((short) 8);
        params.setStyle(ExcelExportMyStylerImpl.class);
        FileOutputStream out = null;
        try {
            workbook = ExcelExportUtil.exportExcel(params, pojoClass, listData);
            out = getExcelFileOut(fileName,uk);
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) out.close();
                if(workbook != null) workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    /**
     * 获取excel的全路径文件名
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    private static FileOutputStream getExcelFileOut(String fileName,String uk) throws Exception{
        if(!fileName.endsWith(".xls")||!fileName.endsWith(".xlsx")) {
            fileName += ".xls";
        }
        String allPath = FileUtils.getTempPath(uk)+fileName;
        File f = FileUtils.createFile(allPath);
        return new FileOutputStream(f);
    }
    private static FileOutputStream getFileOut(String fileName) throws Exception{
        String allPath = FileUtils.getTempPath()+fileName;
        File f = FileUtils.createFile(allPath);
        return new FileOutputStream(f);
    }

    public static void downZip(String zipName,HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        BufferedInputStream bis = null;
        try {
            File f = new File(zipName);
            outputStream = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(f));
            bis.available();
            byte[] bytes = new byte[bis.available()];
            bis.read(bytes);
            response.setContentType("application/octet-stream;chartset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=problem.zip");
            setHeader(response);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream !=null) outputStream.close();
                if(bis !=null) bis.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置文件前端可见
     * @param response
     */
    private static void setHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }
    /**
     * @param fileName
     * @param template
     * @param sheetWithData - sheet : data
     */
    public static void exportExcelWithTemp(String fileName, String template, @Nullable CellWriteHandler writeHandler , Map<String, Map<String, Object>> sheetWithData) {
        File  file = new File(FileUtils.getTempPath_());
        if (!file .exists() || !file .isDirectory())  file .mkdir();
        try (ExcelWriter excelWriter = EasyExcel.write(FileUtils.getTempPath_() + fileName).withTemplate((template)).build()) {
            for (String sheetName : sheetWithData.keySet()) {
                WriteSheet writeSheet;
                if (ObjectUtil.isNotEmpty(writeHandler)){
                     writeSheet = EasyExcel.writerSheet(sheetName).registerWriteHandler(writeHandler).build();
                }else{
                     writeSheet = EasyExcel.writerSheet(sheetName).build();
                }

                FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();

                for (String wrapperName : sheetWithData.get(sheetName).keySet()) {
                    if ("report".equals(wrapperName)){
                        excelWriter.fill( sheetWithData.get(sheetName).get(wrapperName), writeSheet);
                    }else{
                        excelWriter.fill(new FillWrapper(wrapperName, (List)sheetWithData.get(sheetName).get(wrapperName)), fillConfig, writeSheet);
                    }
                }
            }

        }
    }


    public static void simpleDownFile(HttpServletResponse resp, String path, String fileName) {
        try (InputStream is = new FileInputStream(path + fileName); OutputStream os = resp.getOutputStream();) {
            resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            setHeader(resp);
            IOUtils.copy(is, os);
            File f = new File(path + fileName);
            delete(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void downLoad(HttpServletResponse resp, String path, String fileName) {
        try (InputStream is = new FileInputStream(path); OutputStream os = resp.getOutputStream();) {
            resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            setHeader(resp);
            IOUtils.copy(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    @AllArgsConstructor
  public  static class ReportMegerCell implements CellWriteHandler {
        @Override
        public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            String val = cell.getStringCellValue();

            //需要合并的规则
            if (val == null || !val.matches("^[0-9]+#[0-9]+$")) return;
            String[] split = val.split("#");

            //合并起止列索引
            int start = Integer.valueOf(split[0]);
            int end = Integer.valueOf(split[1]);

            if (end != cell.getColumnIndex()) return;

            CellRangeAddress cellAddresses = new CellRangeAddress(
                    cell.getRowIndex()
                    , cell.getRowIndex()
                    , start
                    , end);
            writeSheetHolder.getSheet().addMergedRegionUnsafe(cellAddresses);
        }

    }



}