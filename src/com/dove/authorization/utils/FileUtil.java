package com.dove.authorization.utils;

import java.io.*;
import java.net.URLDecoder;

/**
 * 文件工具类
 *
 * @author happyqing
 */
public class FileUtil {

    /**
     * 获得类的基路径，打成jar包也可以正确获得路径
     *
     * @return
     */
    public static String getBasePath() {
        String filePath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        if (filePath.endsWith(".jar")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            try {
                filePath = URLDecoder.decode(filePath, "UTF-8"); //解决路径中有空格%20的问题  
            } catch (UnsupportedEncodingException ex) {

            }
        }

        File file = new File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    /**
     * 读取文件
     *
     * @param file 文件
     * @return
     */
    public static byte[] readBytes(File file) {
        long len = file.length();
        if (len >= 2147483647L) {
            throw new RuntimeException("File is larger then max array size");
        } else {
            byte[] bytes = new byte[(int) len];
            FileInputStream in = null;

            try {
                in = new FileInputStream(file);
                int readLength = in.read(bytes);
                if ((long) readLength < len) {
                    throw new RuntimeException("File length is larger then read length!");
                }
            } catch (Exception var10) {
                throw new RuntimeException(var10);
            } finally {
                close(in);
            }

            return bytes;
        }
    }

    /**
     * 关闭流
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception var2) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBasePath());
    }
}  