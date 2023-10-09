package com.lan.src.utils;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ParseUtils {
    /**
     * 获取指定盘块的登记项信息
     * @param id 块号
     * @return 结果
     */
    public static List<String> getRegistry(Integer id,DiskContentMapper diskContentMapper) {
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(id);         //查询盘块信息
        String content = diskContent.getContent();                                  //获取登记项字符串
        if(content.isEmpty()) return null;
        String[] split = content.split("/");                                  //切割
        if (split.length==0) {
            return new ArrayList<>();                                               //返回空数组
        }
        return Arrays.stream(split).map(String::valueOf).collect(Collectors.toList());//转换类型，返回
    }

    /**
     *  已切割完毕的登记项进行对象创建、自动赋值
     * @param str 待解析字符串 属性名:值/属性名:值...
     * @param clazz 结果类对象
     * @return 结果
     */
    public static Object parseAttribute(String str,Class<?> clazz){
        try {
            Object obj = clazz.getConstructor().newInstance();      //获取构造方法创建实例
            String[] combination = StrUtils.subUnion(str);          //切割原字符串，获取 属性名:值 的组合
            for (String s : combination) {
                String[] cut = StrUtils.subAttrAndValue(s);         //切割 属性名:值
                Field field = clazz.getDeclaredField(cut[0]);       //获取属性类型(仅基础类型)
                Class<?> fieldClass = field.getType();              //获取属性类对象
                Method method = clazz.getDeclaredMethod(
                        "set" + StrUtils.UpperStr(cut[0]),
                        fieldClass);                                //获取set方法
                method.invoke(obj,handlerFieldType(field,StrUtils.remChar(cut[1],' ')));  //调用方法
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

    /**
     * 检查当前目录是否能保存更多的登记项
     * @param content 内容
     * @param dcm Mapper
     */
    public static boolean checkRegSize(String content,DiskContentMapper dcm){
        //判断是否超过目录项上限
        return content.split("/").length < 8;
    }

    /**
     * 搜索目录
     * @param root 根目录登记项List
     * @param dirs 除根目录外已经切割好的各个目录名称
     * @return 存在：返回最终目录登记项集合，否则返回null
     */
    public static List<String> divePath(List<String> root,String[] dirs,DiskContentMapper diskContentMapper){
        if (root == null||root.isEmpty()) {
            return new ArrayList<>();
        }
        for (String dir : dirs) {                                                                   //按顺序搜索目录
            boolean flag = false;
            for (String reg : root) {                                                               //遍历每个登记项，判断是否存在目标目录
                if (dir.equals(reg.substring(0,3))&&
                        "  8".equals(reg.substring(3,6))) {                                         //存在目标
                    flag = true;
                    root = getRegistry(Integer.parseInt(reg.substring(6,9)),diskContentMapper);     //获取下一个目录的登记项内容
                    if(root==null) root = new ArrayList<>();
                    root.add((reg.substring(6,9)));
                    break;
                }
            }

            if (!flag)                                                                              //不存在
                return null;
        }
        //处理目录为空
        //为空返回空列表，否则返回root
        return Objects.requireNonNullElseGet(root, ArrayList::new);
    }
}
