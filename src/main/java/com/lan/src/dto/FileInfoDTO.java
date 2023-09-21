package com.lan.src.dto;

import com.lan.src.pojo.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfoDTO {
    /**
     * 文件信息
     */
    private FileInfo fileInfo;
    /**
     * 文件内容
     */
    private String content;
}
