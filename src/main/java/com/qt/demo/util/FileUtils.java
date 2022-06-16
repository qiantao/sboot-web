package com.qt.demo.util;

import com.qt.demo.enums.RequestStatusEnum;
import com.qt.demo.exception.MyException;

import java.io.File;
import java.io.IOException;

/**
 * @Author MuYang @Perfma
 * @Date 2022/6/16 16:19
 * @version: V1.0
 */
public class FileUtils {

    /**
     * 新建文件对象（新建父文件夹）
     * @param path
     * @return
     * @throws IOException
     */
    public static File createFile(String path) throws IOException {
        File f = new File(path);
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        if(!f.exists()){
            f.createNewFile();
        }
        return f;
    }

    /**
     * 在当前项目下新建文件
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createFileByName(String fileName) throws IOException {
        String path = System.getProperty("user.dir") +"/"+"file/" + fileName;
        return createFile(path);
    }

    public static File getFile(String fileName) throws IOException {
        String path = System.getProperty("user.dir") +"/"+"file/" + fileName;
        return new File(path);
    }


    public static String getSubfixByImgBase64(String dataPrix){
        String suffix = "";//图片后缀，用以识别哪种格式数据
        //data:image/jpeg;base64,base64编码的jpeg图片数据
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
            suffix = ".jpg";
        }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
            //data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
            //data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
            //data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else {
            throw new MyException(RequestStatusEnum.ERROR);
        }
        return suffix;
    }
    public static String getImgBase64Prefix(String fileName){
        String prefix;
        if(fileName.toLowerCase().endsWith(".jpg")){
            prefix = "data:image/jpeg;";
        }else if(fileName.toLowerCase().endsWith(".ico")){
            //data:image/x-icon;base64,base64编码的icon图片数据
            prefix = "data:image/x-icon;";
        }else if(fileName.toLowerCase().endsWith(".gif")){
            //data:image/gif;base64,base64编码的gif图片数据
            prefix = "data:image/gif;";
        }else if(fileName.toLowerCase().endsWith(".png")){
            //data:image/png;base64,base64编码的png图片数据
            prefix = "data:image/png;";
        }else {
            return "上传图片格式不合法";
        }
        return prefix;
    }
}
