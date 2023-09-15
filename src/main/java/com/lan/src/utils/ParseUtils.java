package com.lan.src.utils;

public class ParseUtils {
    /**
     * 判断登记项类别
     * @param str 登记项
     * @return 0：文件，1：目录
     */
    public static boolean executeType(String str){
        return "8".equals(String.valueOf(str.charAt(5)));
    }

    /**
     * 解析登记项
     * @param str 登记项
     */
    public static void parseAttribute(String str, boolean isDirectory){
        //切割登记项
        String name = str.substring(0,3);
        String type = str.substring(3,5);
        String attribute = String.valueOf(str.charAt(5));
        String startId = String.valueOf(str.charAt(6));
        String len = String.valueOf(str.charAt(7));
        //创建实体类、属性赋值
        //...

        return;
    }
}
