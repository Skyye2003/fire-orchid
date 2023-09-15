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
        //查询
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(3);
        //获取登记项字符串
        String content = diskContent.getContent();
        int conLen = content.length(),start = 0,end = 8;
        //切割
        while(start<conLen&&end<conLen){
            temp.add(content.substring(start,end));
            //指向下一个待切割登记项
            start+=9;
            end+=9;
        }
        //处理最后一个带切割登记项
        temp.add(content.substring(start));
        List<Object> result = new ArrayList<>();
        //生成文件/目录实体类
        for (String str : temp) {

        }
        return Result.ok(result);
    }
}
