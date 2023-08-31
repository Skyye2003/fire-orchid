package com.lan.src.pojo;

import lombok.Data;

@Data
public class FileInfo {
    private Integer id;

    private String filePath;

    private String attribute;

    private Integer startId;

    private Integer size;

    private Integer opType;

    private Integer readDnum;

    private Integer readBnum;

    private Integer writeDnum;

    private Integer writeBnum;


}
