package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dto.CreDirDTO;
import com.lan.src.dto.DelDirDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.Result;
import com.lan.src.service.IDirectoryService;
import com.lan.src.utils.CodeConstants;
import com.lan.src.utils.ParseUtils;
import com.lan.src.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DirectoryServiceImpl implements IDirectoryService {
    @Autowired
    private DiskContentMapper diskContentMapper;

    /**
     * 获取指定盘块种的登记项信息
     * @param id 块号
     * @return 结果
     */
    private List<String> getRegistry(Integer id) {
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(id);//查询盘块信息
        String content = diskContent.getContent();                              //获取登记项字符串
        if(content.isEmpty()) return null;
        String[] split = content.split("/");                                //切割
        return Arrays.stream(split).map(String::valueOf).collect(Collectors.toList());//转换类型，返回
    }

    /**
     * 获取指定目录内容
     * @param path 路径
     * @return 结果
     */
    @Override
    public Result<List<RegistryDto>> listRegistry(String path) {
        List<RegistryDto> result = new ArrayList<>();
        List<String> root = getRegistry(3);                                                     //获取根目录登记项
        if ("/".equals(path)) {
            if (root != null) {
                for (String reg : root) {
                    String recast = StrUtils.subStr(reg);                                           //切割并重组一条登记项
                    RegistryDto registryDto =
                            (RegistryDto) ParseUtils.parseAttribute(recast, RegistryDto.class);     //解析登记项，生成对象
                    result.add(registryDto);                                                        //加入结果列表
                }
            }
        }
        else{
            String pathExRoot = path.substring(1);                           //获取除根目录外的路径
            String[] split = pathExRoot.split("/");                              //切割路径
            for (String searchPath : split) {                                          //获取需要检索的路径
                boolean flag = false;
                if (root != null) {
                    for (String unit : root) {                                             //获取当前目录内容(登记项)，与需要进行检索的路径进行比较
                        if ("  ".equals(unit.substring(3, 5)) &&
                                unit.charAt(5) == '8' &&
                                unit.substring(0, 3).equals(searchPath)) {                 //搜索到需要的登记项
                            flag = true;
                            root = getRegistry(Integer.parseInt(unit.substring(6, 9)));    //获取下一个需要检索的目录
                            break;
                        }
                    }
                }
                if (!flag) {
                    return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);
                }
            }
            if(root==null) return Result.ok("empty");
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

    /**
     * 创建子目录
     * @param creDirDTO 对象
     * @return 结果
     */
    @Override
    public Result<RegistryDto> createDir(CreDirDTO creDirDTO) {
        String dirName = creDirDTO.getDirName();
        Integer startId = creDirDTO.getStartId();

        if (dirName.length()>3)                                                                  //判断名称是否超出长度要求
            return Result.error(CodeConstants.CREATE_ERROR_NAME_OUT_OF_LEN);

        dirName = StrUtils.fillStr(dirName,' ',3,false);                          //填充
        DiskContent curDisk = diskContentMapper.selectByPrimaryKey(startId);                     //获取当前盘块信息

        if (!ParseUtils.checkRegSize(curDisk.getContent(), diskContentMapper))                   //无法添加新的登记项
            return Result.error(CodeConstants.CREATE_ERROR_NO_REG);

        Integer emptyDiskId = ParseUtils.searchEmptyDisk(diskContentMapper);                     //检索空盘块

        if (emptyDiskId == null)                                                                 //无空盘块可用
            return Result.error(CodeConstants.CREATE_ERROR_NO_EMPTY);

        String idString = emptyDiskId.toString();
        idString = StrUtils.fillStr(idString,'0',3,false);                        //填充
        String reg = dirName+"  "+"8"+idString+"000";                                            //生成登记项
        RegistryDto result = (RegistryDto)ParseUtils.parseAttribute(
                StrUtils.subStr(reg), RegistryDto.class);                                        //创建对象
        curDisk.setContent(curDisk.getContent()+"/"+reg);
        diskContentMapper.updateByPrimaryKey(curDisk);                                           //保存登记项
        diskContentMapper.updateByPrimaryKey(new DiskContent(emptyDiskId,-1,""));  //开辟空盘块
        return Result.ok(result);                                                                //返回新建的目录对象
    }

    /**
     * 删除子目录
     * @param delDirDTO 对象
     * @return 结果
     */
    @Override
    public Result<String> deleteDir(DelDirDTO delDirDTO) {
        Integer delStartId = delDirDTO.getDelStartId();
        Integer curStartId = delDirDTO.getCurStartId();
        String delName = delDirDTO.getDelName();
        if (delStartId<=3)
            return Result.error(CodeConstants.DEL_ERROR_DEL_DENIED);                                //不能被删除的盘块
        DiskContent delDisk = diskContentMapper.selectByPrimaryKey(delStartId);
        if (!"".equals(delDisk.getContent()))
            return Result.error(CodeConstants.DEL_ERROR_NOT_EMPTY);                                 //登记项不为空
        try {
            String reg = StrUtils.generateDirReg(delName, delStartId);                              //生成登记项
            DiskContent curDisk = diskContentMapper.selectByPrimaryKey(curStartId);                 //获取当前盘块信息

            if (curDisk == null)
                return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);

            String content = curDisk.getContent();
            content = content.replace(reg, "");                                          //修改登记项信息
            content = content.replace("//","/");
            curDisk.setContent(content);
            delDisk.setStatus(0);
            delDisk.setContent("0");
            diskContentMapper.updateByPrimaryKey(delDisk);                                          //重置被删除的盘块信息
            diskContentMapper.updateByPrimaryKey(curDisk);                                          //更新当前盘块号信息
        } catch (Exception e) {
            return Result.error(CodeConstants.DEL_ERROR_OUT_OF_LEN);                                //名称或起始盘块长度出错
        }
        return Result.ok("删除成功!");
    }
}
