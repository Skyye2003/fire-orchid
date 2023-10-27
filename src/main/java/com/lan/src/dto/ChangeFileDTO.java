package com.lan.src.dto;

import lombok.Data;

/**
 * 更改的文件属性
 */
@Data
public class ChangeFileDTO {
    /**
     * 需要更改文件的目录盘块号
     */
    private Integer diskId;

    /**
     * 需要更改文件的盘块号
     */
    private Integer startId;

    /**
     * 原始的文件名(含后缀)，不是文件路径
     */
    private String oldName;

    /**
     * 更改后的文件名
     */
    private String newName;

    /**
     * 更改后的文件后缀
     */
    private String type;

    /**
     * 更改后的文件属性
     */
    private Integer attribute;
}
