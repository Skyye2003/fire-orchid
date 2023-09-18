package com.lan.src.controller;

import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import com.lan.src.service.IDirectoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.runtime.ObjectMethods;
import java.util.List;

@RestController
@RequestMapping("/dir")
@Slf4j
public class DirectoryController {
    @Autowired
    private IDirectoryService directoryService;

    @PostMapping("/list")
    public Result<List<RegistryDto>> listRegistry(@RequestParam String path){
        return directoryService.listRegistry(path);
    }

    @PostMapping("/create")
    public Result<RegistryDto> createDir(@RequestParam String dirName, @RequestParam Integer startId){
        return directoryService.createDir(dirName,startId);
    }

    @DeleteMapping("/del")
    public Result<String> deleteDir(@RequestParam Integer curStartId,@RequestParam String delName,@RequestParam Integer delStartId){
        return directoryService.deleteDir(curStartId,delName,delStartId);
    }
}
