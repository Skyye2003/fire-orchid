package com.lan.src.dto;

import lombok.Data;

@Data
public class DelFileDTO {
    /**
     * 当前文件盘块号
     */
    private Integer curStartId;
    /**
     * 被删除文件起始盘块号
     */
    private Integer delStartId;
    /**
     * 被删除文件名称
     */
    private String delName;
}
