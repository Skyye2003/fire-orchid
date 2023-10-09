package com.lan.src.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登记项DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryDTO {
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 属性
     */
    private Integer attribute;
    /**
     * 起始盘块号
     */
    private Integer startId;
    /**
     * 长度
     */
    private Integer len;
}
