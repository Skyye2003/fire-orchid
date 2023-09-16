package com.lan.src.service;

import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IDirectoryService {

    Result<List<RegistryDto>> listRegistry(String path);
}
