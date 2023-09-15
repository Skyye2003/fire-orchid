package com.lan.src.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lan.src.dao.FileInfoMapper;
import com.lan.src.pojo.FileInfo;
import com.lan.src.service.IFileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IFileServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileService {
    @Autowired
    private FileInfoMapper FileInfoMapper;


    @Override
    public void hi() {
        FileInfoMapper.insert(new FileInfo());
        log.info("hi");
    }
}
