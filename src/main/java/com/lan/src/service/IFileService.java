package com.lan.src.service;

import com.lan.src.dto.*;
import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IFileService {
    Result<RegistryDto> createFile(CreFileDTO creFileDTO);

    Result<FileInfoDTO> openFile(OpenFileDTO openFileDTO);

    Result<String> delFile(DelFileDTO delFileDTO);
}
