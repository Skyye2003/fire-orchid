package com.lan.src.dto;

import lombok.Data;

@Data
public class WriteFileDTO {
    /**
     * 写文件的起始盘块号
     */
    private Integer fileStartId;
    /**
     * 需要写入的数据
     */
    private String data;
}
