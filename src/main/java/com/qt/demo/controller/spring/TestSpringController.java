package com.qt.demo.controller.spring;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.qt.demo.common.mysql.HKSource;
import com.qt.demo.common.mysql.MsqlDBConnection;
import com.qt.demo.common.mysql.MyBatitsDao;
import com.qt.demo.common.redis.RedisCache;
import com.qt.demo.entity.Person;
import com.qt.demo.manager.MyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@RestController
@RequestMapping("/spring")
@Slf4j
public class TestSpringController {


    @PostMapping("/post/json")
    public String postJson(@RequestBody Map<String,Object> map){
        return JSONUtil.toJsonStr(map);
    }

    @GetMapping("/get/json")
    public String getJson(@RequestParam Map<String,Object> map){
        return JSONUtil.toJsonStr(map);
    }

    @GetMapping("/get/map")
    public Map<String,Object> getMap(@RequestParam Map<String,Object> map){
        return map;
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
                String allPath = path + "data_file1_"+file1.getOriginalFilename();
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

    @PostMapping("/post/param")
    public String a(@RequestParam("key") String key ){
        return key;
    }


}
