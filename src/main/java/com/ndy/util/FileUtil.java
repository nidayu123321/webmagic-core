package com.ndy.util;

import java.io.*;

/**
 * @author nidayu
 * @Description:
 * @date 2015/11/5
 */
public class FileUtil {

    public static String readFile(String filePath){
        return readFile(filePath, "utf-8");
    }

    /**
     * 读取文件
     * @param filePath
     * @param charset
     * @return
     */
    public static String readFile(String filePath, String charset){
        String ret = null;
        try {
            if (charset == null){
                charset = "UTF-8";
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
            StringBuffer sb = new StringBuffer();
            int len=0;
            char[] info = new char[1024];
            while ((len = br.read(info)) != -1) {
                sb.append(new String(info, 0, len));
            }
            ret = sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 写入文件
     * @param filePath
     * @param info
     */
    public static void writeFile (String filePath, String info) throws Exception{
        File fp = new File(filePath.substring(0, filePath.lastIndexOf("/")-1));
        // 创建目录
        if (!fp.exists()) {
            fp.mkdirs();// 目录不存在的情况下，创建目录。
        }
        FileWriter fw = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(info);
        // 不关闭则不会写入
        bw.close();
        fw.close();
    }

    /**
     * 保存InputStream类型的图片验证码
     * @param filePath
     * @param inputStream
     */
    public static void downloadImg(String filePath, InputStream inputStream){
        File file=new File(filePath);
        OutputStream os=null;
        try{
            os=new FileOutputStream(file);
            byte buffer[]=new byte[4*1024];
            int len = 0;
            while((len = inputStream.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
            os.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                os.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){

    }
}
