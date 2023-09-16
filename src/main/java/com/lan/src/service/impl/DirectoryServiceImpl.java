package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import com.lan.src.service.IDirectoryService;
import com.lan.src.utils.ParseUtils;
import com.lan.src.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DirectoryServiceImpl implements IDirectoryService {
    @Autowired
    private DiskContentMapper diskContentMapper;

    private List<String> getRegistry(Integer startId) {
        List<String> temp = new ArrayList<>();
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(startId);//查询盘块信息
        String content = diskContent.getContent();                              //获取登记项字符串
        int conLen = content.length(),start = 0,end = 8;
        while(start<conLen&&end<conLen){                                        //切割
            temp.add(content.substring(start,end));
            start+=9;                                                           //指向下一个待切割登记项
            end+=9;
        }
        temp.add(content.substring(start));                                     //处理最后一个带切割登记项
        return temp;
    }

    @Override
    public Result<List<RegistryDto>> listRegistry(String path) {
        List<RegistryDto> result = new ArrayList<>();
        List<String> root = getRegistry(3);                                                      //获取根目录登记项
        if ("/".equals(path)) {
            for (String str : root) {
                String recast = StrUtils.subStr(str);                                           //切割并重组一条登记项
                RegistryDto registryDto =
                        (RegistryDto) ParseUtils.parseAttribute(recast, RegistryDto.class);     //解析登记项，生成对象
                result.add(registryDto);                                                        //加入结果列表
            }
        }
        else{
            String pathExRoot = path.substring(1);                           //获取除根目录外的路径
            String[] split = pathExRoot.split("/");                              //切割路径
            for (int i = 0 ;i < split.length;i++) {                                    //获取需要检索的路径
                String searchPath = split[i];
                boolean flag = false;
                for (String unit : root) {                                             //获取当前目录内容(登记项)，与需要进行检索的路径进行比较
                    if ("  ".equals(unit.substring(3, 5)) &&
                            unit.charAt(5) == '8' &&
                            unit.substring(0, 3).equals(searchPath)) {                 //搜索到需要的登记项
                        flag = true;
                        root = getRegistry(Integer.parseInt(unit.charAt(6)+""));    //获取下一个需要检索的目录
                        break;
                    }
                }
                if (!flag) {
                    return Result.error("不存在该目录!");
                }
            }
            //找到结果，继续操作
            for (String str : root) {
                String recast = StrUtils.subStr(str);                                           //切割并重组一条登记项
                RegistryDto registryDto =
                        (RegistryDto) ParseUtils.parseAttribute(recast, RegistryDto.class);     //解析登记项，生成对象
                result.add(registryDto);                                                        //加入结果列表
            }
        }
        return Result.ok(result);
    }
}
