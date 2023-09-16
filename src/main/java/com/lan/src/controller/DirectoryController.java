package com.lan.src.controller;

import com.lan.src.pojo.Result;
import com.lan.src.service.IDirectoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/directory")
@Slf4j
public class DirectoryController {
    @Autowired
    private IDirectoryService directoryService;

    /**
     * 获取根目录
     * @return 结果
     */
    @GetMapping("/root")
    public Result<List<Object>> getRoot(){
        return directoryService.getRoot();
    }
}
