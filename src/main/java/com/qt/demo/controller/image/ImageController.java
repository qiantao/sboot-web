package com.qt.demo.controller.image;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.json.JSONUtil;
import com.google.common.io.Files;
import com.qt.demo.entity.UploadBodyDO;
import com.qt.demo.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * 图片处理接口
 */
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @PostMapping("/upload/base64")
    public String upload(@RequestBody List<String> stringList) {
        String base64Data = stringList.get(0);
//        log.info("==上传图片==");
//        log.info("==接收到的数据=="+base64Data);

        String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        if(base64Data==null||"".equals(base64Data)){
            return "上传失败，上传图片数据为空";
        }else {
            String [] d = base64Data.split("base64,");//将字符串分成数组
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else {
                return "上传失败，数据不合法";
            }
        }
        String suffix = FileUtils.getSubfixByImgBase64(dataPrix);

        String uuid = System.currentTimeMillis()+"";
        String tempFileName=uuid+suffix;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(data);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {
                    //调整异常数据
                    b[i]+=256;
                }
            }
            File f = FileUtils.createFileByName(tempFileName);
            OutputStream out = new FileOutputStream(f);
            out.write(b);
            out.flush();
            out.close();
            String imgurl="http://xxxxxxxx/"+tempFileName;
            //imageService.save(imgurl);
            return imgurl;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传图片失败";
        }

    }
    @PostMapping("/upload/form/base64")
    public String upload(@RequestParam("pic") String base64Data,@RequestParam("oisId") String oisId) {
//        log.info("==上传图片==");
//        log.info("==接收到的数据=="+base64Data);

        String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        if(base64Data==null||"".equals(base64Data)){
            return "上传失败，上传图片数据为空";
        }else {
            String [] d = base64Data.split("base64,");//将字符串分成数组
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else {
                return "上传失败，数据不合法";
            }
        }
        String suffix = FileUtils.getSubfixByImgBase64(dataPrix);

        String uuid = System.currentTimeMillis()+"";
        String tempFileName=uuid+suffix;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(data);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {
                    //调整异常数据
                    b[i]+=256;
                }
            }
            File f = FileUtils.createFileByName(tempFileName);
            OutputStream out = new FileOutputStream(f);
            out.write(b);
            out.flush();
            out.close();
            String imgurl="http://xxxxxxxx/"+tempFileName;
            //imageService.save(imgurl);
            return imgurl;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传图片失败";
        }

    }

    @PostMapping("download/base64")
    public String upload() {
        String base64 = "";
        byte[] buffer ;
        FileInputStream fis =null;
        String dataPrix = "";
        try {
            String fileName = "qt.jpg";
            File f = FileUtils.getFile(fileName);
            dataPrix = FileUtils.getImgBase64Prefix(fileName);
            fis = new FileInputStream(f);
            buffer = new byte[(int) f.length()];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length & (numRead = fis.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            System.out.println(offset == buffer.length);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(buffer!=null) {
            String encode = Base64Encoder.encode(buffer);
            return "data:image/jpeg;base64,"+encode;
        }
        return base64;
    }



}
