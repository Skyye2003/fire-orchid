package com.lan.src.pojo;

import lombok.Data;

@Data
public class FileInfo {
    /**
     * 文件id
     */
    private Integer id;

    /**
     * 文件绝对路径
     */
    private String filePath;

    /**
     * 文件属性
     */
    private String attribute;

    /**
     * 起始盘块号
     */
    private Integer startId;

    /**
     * 文件大小（块）
     */
    private Integer size;

    /**
     * 操作类型（0 -> 读，1 -> 写）
     */
    private Integer opType;

    /**
     * 读文件的磁盘盘块号
     */
    private Integer readDnum;

    /**
     * 读文件在磁盘第几字节
     */
    private Integer readBnum;

    /**
     * 写文件的磁盘盘块号
     */
    private Integer writeDnum;

    /**
     * 写文件在磁盘第几字节
     */
    private Integer writeBnum;


}
