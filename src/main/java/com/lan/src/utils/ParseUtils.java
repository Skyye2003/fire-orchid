package com.lan.src.utils;

import com.lan.src.dao.DiskContentMapper;
import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
     *  已切割完毕的登记项进行对象创建、自动赋值
     * @param str 待解析字符串
     * @param clazz 结果类对象
     * @return 结果
     */
    public static Object parseAttribute(String str,Class<?> clazz){
        try {
            Object obj = clazz.getConstructor().newInstance();      //获取构造方法创建实例
            String[] combination = StrUtils.subUnion(str);          //切割原字符串，获取 属性名:值 的组合
            System.out.println("combination: ");
            for (String string : combination) {
                System.out.println(string);
            }
            System.out.println();
            for (String s : combination) {
                String[] cut = StrUtils.subAttrAndValue(s);         //切割 属性名:值
                System.out.print("cut: ");
                for (String string : cut) {
                    System.out.println(string);
                }
                Field field = clazz.getDeclaredField(cut[0]);       //获取属性类型(仅基础类型)
                Class<?> fieldClass = field.getType();              //获取属性类对象
                Method method = clazz.getDeclaredMethod(
                        "set" + StrUtils.UpperStr(cut[0]),
                        fieldClass);                                //获取set方法
                method.invoke(obj,handlerFieldType(field,cut[1]));  //调用方法
            }
        return obj;                                                 //返回结果
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
        String fieldTypeName = field.getType().getName();   //获取类型包名
        return switch (fieldTypeName) {                     //判断类型，进行类型转换
            case "java.lang.String" -> str;
            case "java.lang.Integer", "int" -> Integer.parseInt(str);
            default -> null;
        };
    }

    /**
     * 搜索空闲盘块
     * @param dcm Mapper
     * @return 结果
     */
    public static Integer searchEmptyDisk(DiskContentMapper dcm){
        for (int i = 4; i < 129; i++) {
            Integer status = dcm.selectByPrimaryKey(i).getStatus();
            if(status==0) return i;
        }
        return null;
    }
}
