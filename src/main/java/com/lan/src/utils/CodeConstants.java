package com.lan.src.utils;

public class CodeConstants {
    /**
     * 操作目标不存在
     */
    public static final String ERROR_NO_SUCH_TARGET = "10011: 操作对象不存在!";
    /**
     * 创建成功
     */
    public static final String CREATE_OK = "20010";
    /**
     * 名称/后缀超出长度限制(length = 3)
     */
    public static final String CREATE_ERROR_NAME_OUT_OF_LEN = "20011: 名称/后缀超出长度限制!";
    /**
     * 无空盘块分配
     */
    public static final String CREATE_ERROR_NO_EMPTY = "20012: 无空盘块可分配!";
    /**
     * 当前目录登记项个数已满
     */
    public static final String CREATE_ERROR_NO_REG = "20013: 当前目录登记项个数已满!";
    /**
     * 删除成功
     */
    public static final String DEL_OK = "30010";
    /**
     * 删除超出长度限制(length = 3)
     */
    public static final String DEL_ERROR_OUT_OF_LEN = "30011: 盘块号/目录名称超出长度限制!";
    /**
     * 删除目录不为空
     */
    public static final String DEL_ERROR_NOT_EMPTY = "30012: 目录不为空!";
    /**
     * 不能删除该目录
     */
    public static final String DEL_ERROR_DEL_DENIED = "30013: 不允许被删除的目录!";
    /**
     * 只读文件写打开
     */
    public static final String OPEN_ERROR_TYPE = "40011: 只读写打开!";
    /**
     * 文件名称重复
     */
    public static final String RENAME_ERROR_CONFLICT = "50010: 文件名称已存在！";
}
