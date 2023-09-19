package com.lan.src.dto;

import lombok.Data;

/**
 * 创建文件
 */
@Data
public class CreFileDTO {
    /**
     * 当前盘块号
     */
    private Integer curStartId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型(后缀)
     */
    private String type;
    /**
     * 文件属性
     */
    private Integer attribute;
}
