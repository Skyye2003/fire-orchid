package com.lan.src.utils;

public class StrUtils {
    /**
     * 切割并重组一条登记项，格式：属性名1:值1/属性名2:值2...
     * @param str 待切割登记项
     * @return 结果
     */
    public static String subStr(String str){
        return "name:"+str.substring(0,3)+"/" +
                "type:"+str.substring(3,5)+"/" +
                "attribute:"+ str.charAt(5) +"/" +
                "startId:"+str.substring(6,9)+"/" +
                "len:"+str.substring(9);
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

    /**
     * 填充字符串
     * @param str 原字符串
     * @param c 填充物
     * @param length 填充到多长
     * @param type 填充模式：false：左填充，true：右填充
     * @return 结果
     */
    public static String fillStr(String str,char c,Integer length,boolean type){
        StringBuilder strBuilder = new StringBuilder(str);
        while(strBuilder.length()<length){
            if(!type)                       //左填充
                strBuilder.insert(0,c);
            else                            //右填充
                strBuilder.append(c);
        }
        str = strBuilder.toString();
        return str;
    }

    /**
     * 生成目录登记项
     * @param name 目录名
     * @param startId 起始盘块号
     * @return 结果
     * @throws Exception 超出长度
     */
    public static String generateDirReg(String name,Integer startId) throws Exception {
        if (name.length()>3||startId>128)
            throw new Exception();
        return fillStr(name,' ',3,false)+"  8"+fillStr(startId.toString(),'0',3,false)+"000";
    }

    public static String generateFileReg(String name,String type,Integer attribute,Integer startId) throws Exception {
        if (name.length()>3||startId>128||attribute>7||type.length()>2|| type.isEmpty())
            throw new Exception();
        return fillStr(name,' ',3,false)+           //填充名称
                fillStr(type,' ',2,false)+          //填充类型
                attribute+                                         //文件属性
                fillStr(startId.toString(),'0',3,false)+//填充盘块号
                "001";                                             //默认长度1
    }
}
