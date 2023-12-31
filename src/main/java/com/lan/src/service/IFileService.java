package com.lan.src.service;

import com.lan.src.dto.*;
import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;

@Transactional
public interface IFileService {
    Result<RegistryDTO> createFile(CreFileDTO creFileDTO);

    Result<FileInfoDTO> openFile(OpenFileDTO openFileDTO);

    Result<String> delFile(DelFileDTO delFileDTO);

    Result<FileInfoDTO> writeFile(WriteFileDTO writeFileDTO);

    Result<FileInfoDTO> change(ChangeFileDTO changeFileDTO);
}