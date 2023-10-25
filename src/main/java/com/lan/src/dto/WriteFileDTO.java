package com.lan.src.dto;

import lombok.Data;

@Data
public class WriteFileDTO {
    /**
     * 写文件的id
     */
    private Integer fileId;
    /**
     * 需要写入的数据
     */
    private String data;
}
