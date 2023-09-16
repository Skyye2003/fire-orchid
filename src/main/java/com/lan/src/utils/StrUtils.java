package com.lan.src.utils;

public class StrUtils {
    /**
     * 切割登记项，格式：属性名1:值1,属性名2:值2...
     * @param str 待切割登记项
     * @return 结果
     */
    public static String subStr(String str){
        return "name:"+str.substring(0,3)+"/" +
                "type:"+str.substring(3,5)+"/" +
                "attribute:"+ str.charAt(5) +"/" +
                "startId:"+str.charAt(6)+"/" +
                "len:"+str.charAt(7);
    }

    /**
     * 分割各个属性名:值
     * @param str 原字符串
     * @return 分割结果
     */
    public static String[] subUnion(String str){
        return str.split("/");
    }

    /**
     * 分割各个属性名 值
     * @param str 原字符串
     * @return 分割结果
     */
    public static String[] subAttrAndValue(String str){
        return str.split(":",2);        //分割成两段
    }


    /**
     * 首字母大写
     * @param str 待处理字符串
     * @return 结果
     */
    public static String UpperStr(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
