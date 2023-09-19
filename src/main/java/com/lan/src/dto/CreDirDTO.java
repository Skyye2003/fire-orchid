package com.lan.src.dto;

import lombok.Data;

/**
 * 创建目录
 */
@Data
public class CreDirDTO {
    /**
     * 当前目录盘块号
     */
    private Integer startId;
    /**
     * 目录名称
     */
    private String dirName;
}
