package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dao.FileInfoMapper;
import com.lan.src.dto.CreFileDTO;
import com.lan.src.dto.OpenFileDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import com.lan.src.service.IFileService;
import com.lan.src.utils.CodeConstants;
import com.lan.src.utils.ParseUtils;
import com.lan.src.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class IFileServiceImpl implements IFileService {
    @Autowired
    private DiskContentMapper diskContentMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;

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

    /**
     * 打开文件
     * @param openFileDTO 打开文件DTO
     * @return 结果
     */
    @Override
    public Result<FileInfo> openFile(OpenFileDTO openFileDTO) {
        String path = openFileDTO.getPath();
        Integer rOrW = openFileDTO.getType();
        System.out.println();
        System.out.println("rOrW: "+rOrW);
        System.out.println();
        path = path.substring(1);                           //切割出除根目录外的路径
        String[] dirs = path.split("/");                        //分割
        String[] dirsCopy = Arrays.copyOfRange(dirs, 0, dirs.length - 1);
        List<String> root = ParseUtils.getRegistry(3, diskContentMapper);       //获取根目录内的登记项
        List<String> fin = ParseUtils.divePath(root, dirsCopy, diskContentMapper);
        String[] file = dirs[dirs.length-1].split("\\.");                   //切割名称和后缀

        if (fin == null|| fin.isEmpty())
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);   //找不到目标

        String target = "";
        for (String reg : fin) {                                        //在最后一个目录中搜索目标文件
            if(reg.substring(0,3).equals(StrUtils.fillStr(file[0],' ',3,false))&&
                    reg.substring(3,5).equals(StrUtils.fillStr(file[1],' ',2,false))){
                target = reg;                           //找到目标登记项
                break;
            }
        }

        if (target.isEmpty())
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);   //找不到目标

        //TODO 查找目标，返回结果信息
        FileInfo fileInfo;
        //填写已打开文件表
        if (Integer.parseInt(target.substring(5,6))%2==1&&rOrW==1)             //判断为只读文件
                return Result.error(CodeConstants.OPEN_ERROR_TYPE);

         fileInfo = (FileInfo)ParseUtils.parseAttribute(
                 StrUtils.generateFileInfo(path,target.charAt(5),target.substring(6,9),target.substring(9),rOrW),
                 FileInfo.class);
        fileInfoMapper.insert(fileInfo);
        //返回已打开文件表对象
        return Result.ok(fileInfo);
    }
}
