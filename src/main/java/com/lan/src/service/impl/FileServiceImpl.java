package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dao.FileInfoMapper;
import com.lan.src.dto.CreFileDTO;
import com.lan.src.dto.DelFileDTO;
import com.lan.src.dto.OpenFileDTO;
import com.lan.src.dto.RegistryDto;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.FileInfo;
import com.lan.src.pojo.Result;
import com.lan.src.service.IFileService;
import com.lan.src.utils.CodeConstants;
import com.lan.src.utils.ObjectHandler;
import com.lan.src.utils.ParseUtils;
import com.lan.src.utils.StrUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {
    @Autowired
    private DiskContentMapper diskContentMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    //代理对象
//    private FileServiceImpl proxy;

    //阻塞队列
    private static final BlockingQueue<List<Integer>> clearTasks = new ArrayBlockingQueue<>(1024*1024);

    //执行线程
    private static final ExecutorService CLEAR_EXECUTOR = Executors.newSingleThreadExecutor();

    @PostConstruct
    private void init(){CLEAR_EXECUTOR.submit(new ClearHandler());}

    //执行逻辑
    private class ClearHandler implements Runnable {
        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        public void run() {
            while(true){
                try {
                    //获取阻塞队列中的信息
                    List<Integer> clear = clearTasks.take();
                    //代理对象执行清空操作
                    clearDisk(clear);
                } catch (InterruptedException e) {
                    log.error("处理阻塞队列出现异常...");
                }
            }
        }
    }

    /**
     * 清理盘块
     * @param clear 带清理盘块号list
     */
    public void clearDisk(List<Integer> clear) {
        for (Integer i : clear) {
            DiskContent diskContent = diskContentMapper.selectByPrimaryKey(i);
            diskContent.setContent("0");
            diskContent.setStatus(0);
            diskContentMapper.updateByPrimaryKey(diskContent);
        }
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

    /**
     * 打开文件
     * @param openFileDTO 打开文件DTO
     * @return 结果
     */
    @Override
    public Result<FileInfo> openFile(OpenFileDTO openFileDTO) {
        String path = openFileDTO.getPath();
        Integer rOrW = openFileDTO.getType();
        path = path.substring(1);                                         //切割出除根目录外的路径
        String[] dirs = path.split("/");                                      //分割
        String[] dirsCopy = Arrays.copyOfRange(dirs, 0, dirs.length - 1);
        List<String> root = ParseUtils.getRegistry(3, diskContentMapper);        //获取根目录内的登记项
        List<String> fin = ParseUtils.divePath(root, dirsCopy, diskContentMapper);
        String[] file = dirs[dirs.length-1].split("\\.");                     //切割名称和后缀

        if (fin == null|| fin.isEmpty())
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);                //找不到目标

        String target = "";
        for (String reg : fin) {                                                    //在最后一个目录中搜索目标文件
            if(reg.substring(0,3).equals(StrUtils.fillStr(file[0],' ',3,false))&&
                    reg.substring(3,5).equals(StrUtils.fillStr(file[1],' ',2,false))){
                target = reg;                           //找到目标登记项
                break;
            }
        }

        if (target.isEmpty())
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);                //找不到目标

        FileInfo fileInfo;
        //填写已打开文件表
        if (Integer.parseInt(target.substring(5,6))%2==1&&rOrW==1)                  //判断为只读文件
                return Result.error(CodeConstants.OPEN_ERROR_TYPE);                 //只读文件写打开错误

        fileInfo = (FileInfo)ParseUtils.parseAttribute(
                StrUtils.generateFileInfo(path,target.charAt(5),target.substring(6,9),target.substring(9),rOrW),
                FileInfo.class);
        ObjectHandler.handlePoint(fileInfo, diskContentMapper);
        fileInfoMapper.insert(fileInfo);
        //返回已打开文件表对象
        return Result.ok(fileInfo);
    }

    /**
     * 删除文件
     * @param delFileDTO 删除文件对象
     * @return 结果
     */
    @Override
    public Result<String> delFile(DelFileDTO delFileDTO) {
        List<Integer> clearDisk = new ArrayList<>();
        Integer delStartId = delFileDTO.getDelStartId();
        Integer curStartId = delFileDTO.getCurStartId();
        String delName = delFileDTO.getDelName();
        Integer status = diskContentMapper.selectByPrimaryKey(delStartId).getStatus();
        clearDisk.add(delStartId);
        while(status!=-1){                                                          //查找所有需要删除的盘块号
            clearDisk.add(status);                                                  //加入待删除列表
            status = diskContentMapper.selectByPrimaryKey(status).getStatus();
        }

        clearTasks.add(clearDisk);                                                  //加入到删除任务，等待删除

        fileInfoMapper.deleteByPrimaryKey(delStartId);                              //删除对应的已打开文件表

        //获取当前类的代理对象并赋值给proxy
        /*
          AopContext中存在一个ThreadLocal类管理当前线程使用的对象
          其中存放着代理对象
         */
//        proxy = (FileServiceImpl) AopContext.currentProxy();

        DiskContent curDisk = diskContentMapper.selectByPrimaryKey(curStartId);
        String curDiskContent = curDisk.getContent();
        String[] cut = delName.split("\\.");                    //分割名称、后缀
        String[] contentSplit = curDiskContent.split("/");      //分割出各个登记项
        for (String content : contentSplit) {                         //搜索匹配的登记项
            if (content.substring(0,5).equals(cut[0]+cut[1])) {       //找到
                curDiskContent = curDiskContent.replace(content, "");
                curDiskContent = curDiskContent.replace("//","/");
                if(curDiskContent.charAt(0)=='/') curDiskContent = curDiskContent.substring(1);
                curDisk.setContent(curDiskContent);
                diskContentMapper.updateByPrimaryKey(curDisk);        //更新
                return Result.ok("删除成功!");
            }
        }
        //未找到
        return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);
    }
}