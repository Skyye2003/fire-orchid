package com.lan.src.service;

import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IDirectoryService {

    Result<List<RegistryDto>> listRegistry(String path);

    Result<RegistryDto> createDir(String dirName, Integer startId);

    Result<String> deleteDir(Integer curStartId,String delName,Integer delStartId);
}
