package com.lan.src.controller;

import com.lan.src.dto.*;
import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import com.lan.src.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
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
     * 获取根目录下的文件和目录信息
     * @return
     */
    @GetMapping(value = "/root")
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
        //disk_content里面的前两块盘的content为空，只用于占位
        return Result.ok(fileInfoList);
    }

    @PostMapping("/create")
    public Result<RegistryDTO> createFile(@RequestBody CreFileDTO creFileDTO){
        return fileService.createFile(creFileDTO);
    }

    @PostMapping("/del")
    public Result<String> delFile(@RequestBody DelFileDTO delFileDTO){
        return fileService.delFile(delFileDTO);
    }

    @PostMapping("/open")
    public Result<FileInfoDTO> openFile(@RequestBody OpenFileDTO openFileDTO){
        return fileService.openFile(openFileDTO);
    }

    @PostMapping("/write")
    public Result<FileInfoDTO> writeFile(@RequestBody WriteFileDTO writeFileDTO){
        return fileService.writeFile(writeFileDTO);
    }

    @PostMapping("/change")
    public Result<FileInfoDTO> changeFile(@RequestBody ChangeFileDTO changeFileDTO) {
        return fileService.change(changeFileDTO);
    }
}
