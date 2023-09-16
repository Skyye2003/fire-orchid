package com.lan.src.utils;

import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *  已切割完毕的登记项
     * @param str 待解析登记项
     * @param clazz 结果类对象
     * @return 结果
     */
    public static Object parseAttribute(String str,Class<?> clazz){
        try {
            //获取构造方法创建实例
            Object obj = clazz.getConstructor().newInstance();
            //切割原字符串，获取 属性名:值 的组合
            String[] combination = StrUtils.subUnion(str);
            for (String s : combination) {
                //切割 属性名:值
                String[] cut = StrUtils.subAttrAndValue(s);
                //获取属性类型(仅基础类型)
                Field field = clazz.getDeclaredField(cut[0]);
                //获取属性类对象
                Class<?> fieldClass = field.getType();
                //获取set方法
                Method method = clazz.getDeclaredMethod("set" + StrUtils.UpperStr(cut[0]), fieldClass);
                //调用方法
                method.invoke(obj,handlerFieldType(field,cut[1]));
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 值类型转换
     * @param field 属性类
     * @param str 值
     * @return 转换结果
     */
    private static Object handlerFieldType(Field field, String str){
        //获取类型包名
        String fieldTypeName = field.getType().getName();
        return switch (fieldTypeName) {
            case "java.lang.String" -> str;
            case "java.lang.Integer", "int" -> Integer.parseInt(str);
            default -> null;
        };
    }
}
