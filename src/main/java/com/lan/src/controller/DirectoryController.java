package com.lan.src.controller;

import com.lan.src.dto.CreDirDTO;
import com.lan.src.dto.DelDirDTO;
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

    @GetMapping("/list")
    public Result<List<RegistryDto>> listRegistry(@RequestParam String path){
        return directoryService.listRegistry(path);
    }

    @PostMapping("/create")
    public Result<RegistryDto> createDir(@RequestBody CreDirDTO creDirDTO){
        return directoryService.createDir(creDirDTO);
    }

    @DeleteMapping("/del")
    public Result<String> deleteDir(@RequestBody DelDirDTO delDirDTO){
        return directoryService.deleteDir(delDirDTO);
    }
}
