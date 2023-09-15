package com.lan.src.service.impl;

import com.lan.src.pojo.Result;
import com.lan.src.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IFileServiceImpl implements IFileService {
    /**
     * 获取文件内容
     * @param fileAttribute 文件属性
     * @return 文件解析结果
     */
    @Override
    public Result<String> showFile(String fileAttribute) {

        return null;
    }
}
