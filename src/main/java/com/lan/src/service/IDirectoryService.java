package com.lan.src.service;

import com.lan.src.dto.CreDirDTO;
import com.lan.src.dto.DelDirDTO;
import com.lan.src.dto.DirContentDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IDirectoryService {

    Result<DirContentDTO> listRegistry(String path);

    Result<RegistryDto> createDir(CreDirDTO creDirDTO);

    Result<String> deleteDir(DelDirDTO delDirDTO);
}
