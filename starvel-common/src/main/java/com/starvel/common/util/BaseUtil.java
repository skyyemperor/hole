package com.starvel.common.util;


import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class BaseUtil {

    public static <T> T getNewInstance(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            System.out.println("公用方法：实例化对象异常：" + e.getMessage());
            e.printStackTrace();
        }
        return t;
    }

    //去除空格并在字符间添加%
    public static String removeBlankSpace(String str) {
        StringBuilder ansStr = new StringBuilder("%");
        str = str == null ? "" : str;
        str = str.replaceAll("\\s*", "");
        for (char c : str.toCharArray()) {
            ansStr.append(c).append("%");
        }
        return ansStr.toString();
    }


    public static boolean NOTNULL(Object... strs) {
        for (Object str : strs) {
            if (str == null || "".equals(str.toString().trim())) {
                return false;
            }
        }
        return true;
    }


    /**
     * 克隆对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();
        // 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
    }

    public static String encodeByMd5(Object... args) {
        String data = Arrays.toString(args);
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                if ((b & 0xff) < 16)
                    sb.append("0");
                sb.append(Long.toString(b & 0xff, 16));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}