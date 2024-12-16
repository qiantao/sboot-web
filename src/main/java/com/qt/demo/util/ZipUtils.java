package com.qt.demo.util;


/**
 * @Author MuYang 
 * @Date 2022/6/22 17:45
 * @version: V1.0
 */
public class ZipUtils {

    /**
     * 压缩文件
     * @param path 需要压缩的文件路
     * @param targetPath 压缩包存放路径
     */
    public static String zip(String path,String targetPath){
        AntZip zip = new AntZip();
        return zip.doZip(path,targetPath);
    }

}