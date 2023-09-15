package com.lan.src.service;

import com.lan.src.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IFileService {

    Result<String> showFile(String fileAttribute);
}
