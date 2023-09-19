package com.lan.src.dto;

import lombok.Data;

/**
 * 删除目录
 */
@Data
public class DelDirDTO {
    /**
     * 当前目录盘块号
     */
    private Integer curStartId;
    /**
     * 被删除目录盘块号
     */
    private Integer delStartId;
    /**
     * 被删除目录名称
     */
    private String delName;
}
