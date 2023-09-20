package com.lan.src.service;

import com.lan.src.dto.CreFileDTO;
import com.lan.src.dto.DelFileDTO;
import com.lan.src.dto.OpenFileDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IFileService {
    Result<RegistryDto> createFile(CreFileDTO creFileDTO);

    Result<FileInfo> openFile(OpenFileDTO openFileDTO);

    Result<String> delFile(DelFileDTO delFileDTO);
}
