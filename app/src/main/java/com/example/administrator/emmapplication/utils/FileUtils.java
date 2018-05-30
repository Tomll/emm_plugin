package com.example.administrator.emmapplication.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FileUtils {

    /**
     * *  生成文件
     * *  @param fileName
     */
    public static boolean createFile(File fileName) {
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @throws IOException
     * @description 读文件
     */
    public static String readTxtFile(File fileName) throws IOException {
        String result = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        fileReader = new FileReader(fileName);
        bufferedReader = new BufferedReader(fileReader);

        String read = null;
        int count = 0;
        while ((read = bufferedReader.readLine()) != null) {
            result = result + count + read + "\r\n";
            count++;
        }

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        if (fileReader != null) {
            fileReader.close();
        }

        System.out.println("¶ÁÈ¡ÎÄ¼þµÄÄÚÈÝÊÇ£º " + "\r\n" + result);
        return result;
    }

    /**
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @description 写文件
     */
    public static boolean writeTxtFile(String content, File fileName) throws UnsupportedEncodingException, IOException {
        FileOutputStream o = null;
        o = new FileOutputStream(fileName);
        o.write(content.getBytes("UTF-8"));
        o.close();
        return true;
    }

}
