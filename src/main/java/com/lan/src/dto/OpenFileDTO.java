package com.lan.src.dto;

import lombok.Data;

@Data
public class OpenFileDTO {
    /**
     * 路径
     */
    private String path;
    /**
     * 0读1写
     */
    private Integer type;
}