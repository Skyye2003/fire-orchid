package com.lan.src.controller;

import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import com.lan.src.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *  文件相关接口
 */

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 获取文件列表
     * @return
     */
    @GetMapping(value = "/list")
    public Result<List<FileInfo>> list() {
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(i);
            fileInfo.setFilePath("D:\\test\\" + i + ".txt");
            fileInfo.setAttribute("rw");
            fileInfo.setStartId(i);
            fileInfo.setSize(1);
            fileInfo.setOpType(0);
            fileInfo.setReadDnum(0);
            fileInfo.setReadBnum(0);
            fileInfo.setWriteDnum(0);
            fileInfo.setWriteBnum(0);
            fileInfoList.add(fileInfo);
        }
        return Result.ok(fileInfoList);
    }
}
