package com.lan.src.service;

import com.lan.src.dto.CreFileDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IFileService {

    Result<String> showFile(String fileAttribute);

    Result<RegistryDto> createFile(CreFileDTO creFileDTO);
}
