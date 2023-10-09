package com.lan.src.dto;

import java.util.List;

public class DirContentDTO {
    /**
     * 当前目录起始盘块号
     */
    public Integer curDirStartId;
    /**
     * 目录内容列表
     */
    public List<RegistryDTO> list;
}
