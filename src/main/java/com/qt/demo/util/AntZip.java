package com.qt.demo.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

/**
 * @Author MuYang 
 * @Date 2022/6/22 17:58
 * @version: V1.0
 */
public class AntZip {
    private ZipFile zipFile;
    private ZipOutputStream zipOut;     //压缩Zip
    private ZipEntry zipEntry;
    private static int bufSize;    //size of bytes
    private byte[] buf;
    private int readedBytes;

    private BufferedOutputStream bos;

    private FileOutputStream fos;

    public AntZip() {
        this(512);
    }

    public AntZip(int bufSize) {
        this.bufSize = bufSize;
        this.buf = new byte[this.bufSize];
    }

    public String doZip(String zipDirectory,String targetPath) {//zipDirectoryPath:需要压缩的文件夹名
        try {
            File zipDir = new File(zipDirectory);
            String zipFileName = targetPath+"/问题列表.zip";//压缩后生成的zip文件名
//            String zipFileName = targetPath+"/"+zipDir.getName() + ".zip";//压缩后生成的zip文件名
            File f = FileUtils.createFile(zipFileName);
            fos = new FileOutputStream(f);
            this.bos = new BufferedOutputStream(fos);
            this.zipOut = new ZipOutputStream(new BufferedOutputStream(bos));
            handleDir(zipDir);
            return zipFileName;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException("压缩文件出错");
        }finally {
            try {
                if(this.zipOut!=null) this.zipOut.close();
                if(this.fos!=null) this.fos.close();
                if(this.bos!=null) this.bos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void handleDir(File dir) throws IOException {
        handleDir(dir,dir.toString());
    }

    private void handleDir(File dir,String prefixPath) throws IOException {
        FileInputStream fileIn;
        File[] files;

        files = dir.listFiles();

        if (files.length == 0) {//如果目录为空,则单独创建之.
            //ZipEntry的isDirectory()方法中,目录以"/"结尾.
            this.zipOut.putNextEntry(new ZipEntry(dir.toString() + "/"));
            this.zipOut.closeEntry();
        } else {//如果目录不为空,则分别处理目录和文件.
            for (File fileName : files) {

                if (fileName.isDirectory()) {
                    handleDir(fileName,prefixPath);
                } else {
                    fileIn = new FileInputStream(fileName);
                    this.zipOut.putNextEntry(new ZipEntry(fileName.toString().substring(prefixPath.length() + 1)));

                    while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
                        this.zipOut.write(this.buf, 0, this.readedBytes);
                    }

                    this.zipOut.closeEntry();
                }
            }
        }
    }

    //解压指定zip文件
    public void unZip(String unZipfileName) {//unZipfileName需要解压的zip文件名
        FileOutputStream fileOut;
        File file;
        InputStream inputStream;

        try {
            this.zipFile = new ZipFile(unZipfileName);

            for (Enumeration entries = this.zipFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                file = new File(entry.getName());

                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    //如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }

                    inputStream = zipFile.getInputStream(entry);

                    fileOut = new FileOutputStream(file);
                    while ((this.readedBytes = inputStream.read(this.buf)) > 0) {
                        fileOut.write(this.buf, 0, this.readedBytes);
                    }
                    fileOut.close();

                    inputStream.close();
                }
            }
            this.zipFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //设置缓冲区大小
    public void setBufSize(int bufSize) {
        this.bufSize = bufSize;
    }

}