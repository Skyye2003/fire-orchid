package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dto.CreFileDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import com.lan.src.service.IFileService;
import com.lan.src.utils.CodeConstants;
import com.lan.src.utils.ParseUtils;
import com.lan.src.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IFileServiceImpl implements IFileService {
    @Autowired
    private DiskContentMapper diskContentMapper;

    /**
     * 获取文件内容
     * @param fileAttribute 文件属性
     * @return 文件解析结果
     */
    @Override
    public Result<String> showFile(String fileAttribute) {

        return null;
    }

    /**
     * 创建文件
     * @param creFileDTO 文件对象
     * @return 结果
     */
    @Override
    public Result<RegistryDto> createFile(CreFileDTO creFileDTO) {
        Integer curStartId = creFileDTO.getCurStartId();
        String fileName = creFileDTO.getFileName();
        String type = creFileDTO.getType();
        Integer attribute = creFileDTO.getAttribute();
        DiskContent curDisk = diskContentMapper.selectByPrimaryKey(curStartId);                      //获取当前盘块信息

        if (!ParseUtils.checkRegSize(curDisk.getContent(),diskContentMapper))                        //无空间可添加新登记项
            return Result.error(CodeConstants.CREATE_ERROR_NO_REG);

        try {
            Integer emptyDiskId = ParseUtils.searchEmptyDisk(diskContentMapper);                     //搜索空盘块号

            if (emptyDiskId == null)
                return Result.error(CodeConstants.CREATE_ERROR_NO_EMPTY);                            //无空盘块可用

            String reg = StrUtils.generateFileReg(fileName, type, attribute, emptyDiskId);           //生成登记项
            curDisk.setContent(curDisk.getContent()+'/'+reg);
            diskContentMapper.updateByPrimaryKey(curDisk);                                           //更新盘块登记项
            RegistryDto result = (RegistryDto)ParseUtils.parseAttribute(
                    StrUtils.subStr(reg), RegistryDto.class);                                        //创建对象
            diskContentMapper.updateByPrimaryKey(new DiskContent(emptyDiskId,-1,""));  //开辟空盘块
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(CodeConstants.CREATE_ERROR_NAME_OUT_OF_LEN);
        }
    }
}
