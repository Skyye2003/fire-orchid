package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import com.lan.src.service.IDirectoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DirectoryServiceImpl implements IDirectoryService {
    @Autowired
    private DiskContentMapper diskContentMapper;

    @Override
    public Result<List<Object>> getRoot() {
        List<String> temp = new ArrayList<>();
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(3);  //查询
        String content = diskContent.getContent();                              //获取登记项字符串
        int conLen = content.length(),start = 0,end = 8;
        while(start<conLen&&end<conLen){                                        //切割
            temp.add(content.substring(start,end));
            start+=9;                                                           //指向下一个待切割登记项
            end+=9;
        }
        temp.add(content.substring(start));                                     //处理最后一个带切割登记项
        List<Object> result = new ArrayList<>();
        for (String str : temp) {                                               //生成文件/目录实体类

        }
        return Result.ok(result);
    }
}
