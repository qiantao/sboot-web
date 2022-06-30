package com.qt.demo.controller.image;

import cn.hutool.json.JSONUtil;
import com.google.common.io.Files;
import com.qt.demo.entity.UploadBodyDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 文件处理接口
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
//            String path =  System.getProperty("user.dir") +"/"+"file/";
//            // Get the file and save it somewhere
//            byte[] bytes = file.getBytes();
////            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            String allPath = path + uploadBodyDO.getFileName();
//            File f = new File(allPath);
//            if(!f.getParentFile().exists()){
//                f.getParentFile().mkdirs();
//            }
//            if(!f.exists()){
//                f.createNewFile();
//            }
//            Files.write(bytes, f);

//            log.info("upload file success, path = {}",allPath);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }

    @PostMapping("/uploadTemp")
    public String uploadTemp(@RequestParam("file") MultipartFile file,
                         UploadBodyDO uploadBodyDO) {
        log.info("request body ={}", JSONUtil.toJsonStr(uploadBodyDO));
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            String path =  System.getProperty("user.dir") +"/"+"file/";
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            String allPath = path + uploadBodyDO.getFileName();
            File f = new File(allPath);
            if(!f.getParentFile().exists()){
                f.getParentFile().mkdirs();
            }
            if(!f.exists()){
                f.createNewFile();
            }
            Files.write(bytes, f);

            log.info("upload file success, path = {}",allPath);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }

    @PostMapping("/download")
    public void logDownload(@RequestParam("fileName") String name, HttpServletResponse response) throws Exception {
        String path =  System.getProperty("user.dir") +"/"+"file/";
        String allPath = path + name;
        File file = new File(allPath);

        if (!file.exists()) {
            throw new RuntimeException(name + "文件不存在");
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + name);

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
    }

    @GetMapping("/download/get")
    public void logDownloadGet(@RequestParam("fileName") String name, HttpServletResponse response) throws Exception {
        String path =  System.getProperty("user.dir") +"/"+"file/";
        String allPath = path + name;
        File file = new File(allPath);

        if (!file.exists()) {
            throw new RuntimeException(name + "文件不存在");
        }
//        response.setContentType("application/force-download");
//        response.addHeader("Content-Disposition", "attachment;fileName=" + name);

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            ServletOutputStream  os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
    }

}
