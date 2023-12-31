package com.lan.src.service.impl;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dao.FileInfoMapper;
import com.lan.src.dto.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
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
//                    System.out.println("get it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+clear.get(0));
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
    public Result<RegistryDTO> createFile(CreFileDTO creFileDTO) {
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

            if("".equals(curDisk.getContent())) curDisk.setContent(reg);
            else curDisk.setContent(curDisk.getContent()+'/'+reg);

            diskContentMapper.updateByPrimaryKey(curDisk);                                           //更新盘块登记项
            RegistryDTO result = (RegistryDTO)ParseUtils.parseAttribute(
                    StrUtils.subStr(reg), RegistryDTO.class);                                        //创建对象
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
    public Result<FileInfoDTO> openFile(OpenFileDTO openFileDTO) {
        String path = openFileDTO.getPath();
        Integer rOrW = openFileDTO.getType();
        path = path.substring(1);                                         //切割出除根目录外的路径
        String[] dirs = path.split("/");                                      //分割目录名
        String[] dirsCopy = Arrays.copyOfRange(dirs, 0, dirs.length - 1);  //复制
        List<String> root = ParseUtils.getRegistry(3, diskContentMapper);        //获取根目录内的登记项
        List<String> fin = ParseUtils.divePath(root, dirsCopy, diskContentMapper);
        if(fin == null){                                                            //不存在对应路径
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET+":路径");
        }
        fin.remove(fin.size()-1);
        String[] file = dirs[dirs.length-1].split("\\.");                     //切割名称和后缀

        if (fin.isEmpty())
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET+":路径");    //找不到目标

        System.out.println("fin: "+fin);

        String target = "";
        for (String reg : fin) {                                                    //在最后一个目录中搜索目标文件
            if(reg.substring(0,3).equals(StrUtils.fillStr(file[0],' ',3,false))&&
                    reg.substring(3,5).equals(StrUtils.fillStr(file[1],' ',2,false))){
                target = reg;                                                       //找到目标登记项
                break;
            }
        }

        //未找到登记项
        if (target.isEmpty())
            return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET+":文件");                //找不到目标

        if (Integer.parseInt(target.substring(5,6))%2==1&&rOrW==1)                  //判断为只读文件
            return Result.error(CodeConstants.OPEN_ERROR_TYPE);                     //只读文件写打开错误

        FileInfo fileInfo = fileInfoMapper.selectByPrimaryKey(Integer.parseInt(target.substring(6, 9)));
        if (fileInfo == null) {
            //填写已打开文件表
            fileInfo = (FileInfo)ParseUtils.parseAttribute(
                    StrUtils.generateFileInfo(path,target.charAt(5),target.substring(6,9),target.substring(9),rOrW),
                    FileInfo.class);
            ObjectHandler.handlePoint(fileInfo, diskContentMapper);
            fileInfoMapper.insert(fileInfo);                                            //保存打开文件表到数据库
        }
        return Result.ok(new FileInfoDTO(fileInfo,handleContent(fileInfo)));        //返回已打开文件表对象
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

        DiskContent curDisk = diskContentMapper.selectByPrimaryKey(curStartId);
        String curDiskContent = curDisk.getContent();
        String[] cut = delName.split("\\.");                    //分割名称、后缀
        cut[0] = StrUtils.fillStr(cut[0],' ',3,false); //填充名称
        cut[1] = StrUtils.fillStr(cut[1],' ',2,false); //填充后缀
        String[] contentSplit = curDiskContent.split("/");      //分割出各个登记项
        for (String content : contentSplit) {                         //搜索匹配的登记项
            if (content.substring(0,5).equals(cut[0] + cut[1])) {       //找到
                fileInfoMapper.deleteByPrimaryKey(delStartId);                              //删除对应的已打开文件表

                clearDisk.add(delStartId);                                                  //加入盘块清空列表
                while(status!=-1){                                                          //查找所有需要删除的盘块号
                    clearDisk.add(status);                                                  //加入待删除列表
                    status = diskContentMapper.selectByPrimaryKey(status).getStatus();
                }
                clearTasks.add(clearDisk);                                                  //加入到删除任务，等待删除

                curDiskContent = curDiskContent.replace(content, "");
                curDiskContent = curDiskContent.replace("//","/");
                if(!curDiskContent.isEmpty()){
                    //清除头'/'
                    if(curDiskContent.charAt(0)=='/') curDiskContent = curDiskContent.substring(1);
                    //清除尾'/'
                    if(curDiskContent.charAt(curDiskContent.length()-1)=='/') curDiskContent = curDiskContent.substring(0,curDiskContent.length()-1);
                }
                curDisk.setContent(curDiskContent);
                diskContentMapper.updateByPrimaryKey(curDisk);        //更新
                return Result.ok("删除成功!");
            }
        }
        //未找到
        return Result.error(CodeConstants.ERROR_NO_SUCH_TARGET);
    }

    /**
     * 获取文件内容
     * @param fileInfo 打开文件表对象
     * @return 结果
     */
    private String handleContent(FileInfo fileInfo){
        Integer startId = fileInfo.getStartId();
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(startId);
        Integer status = diskContent.getStatus();
        StringBuilder result = new StringBuilder(diskContent.getContent());
        while(status!=-1){                                                  //未到最后一个盘块
            diskContent = diskContentMapper.selectByPrimaryKey(status);     //定位下一个盘块
            result.append(diskContent.getContent());                        //添加下一个盘块的内容
            status = diskContent.getStatus();                               //转至下一个盘块
        }
        return result.toString();                                           //返回结果
    }


    public Result<FileInfoDTO> writeFile(WriteFileDTO writeFileDTO) {
        Integer fileStartId = writeFileDTO.getFileStartId();
        String data = writeFileDTO.getData();
        FileInfo fileInfo = fileInfoMapper.selectByStartId(fileStartId); //根据文件起始盘块号获取fileInfo对象
        Integer currentBlock = fileInfo.getWriteDnum(); //获取写文件的盘块号
        Integer writePointer = fileInfo.getWriteBnum(); //获取文件的写指针位置（当前磁盘的第几字节）
        Integer fileLength = fileInfo.getSize(); //获取文件当前长度（占用的磁盘块数）
        DiskContent diskContent = diskContentMapper.selectByPrimaryKey(currentBlock);
        String content = diskContent.getContent(); //获取当前盘块的内容

        //检查是否有足够的空间写入数据
        if (currentBlock != -1 && writePointer + data.length() > 64){
            //如果当前盘块已满，创建一个新的盘块
            Integer newBlock = ParseUtils.searchEmptyDisk(diskContentMapper);
            if (newBlock == null) {
                return Result.error(CodeConstants.CREATE_ERROR_NO_EMPTY); //磁盘空间不足
            }
            //更新当前盘块的状态，指向新的盘块
            diskContent.setStatus(newBlock);
            diskContentMapper.updateByPrimaryKey(diskContent);
            //更新当前盘块内容
            diskContent = diskContentMapper.selectByPrimaryKey(newBlock);
            //更新当前盘块的状态为-1
            diskContent.setStatus(-1);
            content = data;
            //更新文件长度（占用的盘块数）
            fileInfo.setSize(fileLength + 1);
            //更新文件写指针盘块
            fileInfo.setWriteDnum(newBlock);
            //更新文件写指针位置
            fileInfo.setWriteBnum(data.length());
        } else {
            //向当前盘块追加数据
            content += data;
            fileInfo.setWriteBnum(writePointer + data.length());
        }

        //更新盘块的内容
        diskContent.setContent(content);
        diskContentMapper.updateByPrimaryKey(diskContent);
        //更新文件信息
        fileInfoMapper.updateByPrimaryKey(fileInfo);
        return Result.ok(new FileInfoDTO(fileInfo, handleContent(fileInfo)));
    }

    /**
     * 更改文件的属性
     * @param changeFileDTO 更改的文件对象
     * @return 结果
     */
    public Result<FileInfoDTO> change (ChangeFileDTO changeFileDTO) {
        Integer diskId = changeFileDTO.getDiskId(); //当前文件的目录盘块号
        Integer startId = changeFileDTO.getStartId(); //当前文件起始盘块号
        String oldName = changeFileDTO.getOldName();
        String newName = changeFileDTO.getNewName();
        String type = changeFileDTO.getType();
        Integer attribute = changeFileDTO.getAttribute();
        FileInfo fileInfo = fileInfoMapper.selectByStartId(startId); //根据文件起始盘块号获取fileInfo对象

        DiskContent curDisk = diskContentMapper.selectByPrimaryKey(diskId); //获取当前盘块
        String curDiskContent = curDisk.getContent(); //当前盘块内容
        String[] contentSplit = curDiskContent.split("/"); //分割出各个登记项

        String[] change = oldName.split("\\."); //分割名称、后缀
        change[0] = StrUtils.fillStr(change[0],' ',3,false); //填充名称
        change[1] = StrUtils.fillStr(change[1],' ',2,false); //填充后缀

        for (String content : contentSplit) {
            if (content.substring(0, 5).equals(newName + type)) {
                return Result.error(CodeConstants.RENAME_ERROR_CONFLICT);
            }
        }

        try {
            String reg = StrUtils.generateFileReg(newName, type, attribute, startId); //生成更改后的登记项
            String newContent = "";
            for (String content : contentSplit) { //搜索匹配的登记项
                if (content.substring(0,5).equals(change[0] + change[1])) { //匹配到则替换为新的登记项
                    if (newContent.isEmpty()) {
                        newContent += reg;
                    } else {
                        newContent += '/' + reg;
                    }
                } else {
                    if (newContent.isEmpty()) {
                        newContent += content;
                    } else {
                        newContent += '/' + content;
                    }
                }
            }

            String oldFilePath = fileInfo.getFilePath();
            String[] parts = oldFilePath.split("/"); //使用"/"进行分割
            String newFilePath = String.join("/", Arrays.copyOf(parts, parts.length - 1)) + "/" + newName + "." + type; //将原路径的文件名更新为新文件名
            fileInfo.setFilePath(newFilePath);
            fileInfo.setAttribute(attribute.toString()); //更新文件属性
            fileInfoMapper.updateByPrimaryKey(fileInfo);

            curDisk.setContent(newContent);
            diskContentMapper.updateByPrimaryKey(curDisk); //更新盘块登记项

            return Result.ok(new FileInfoDTO(fileInfo, handleContent(fileInfo)));
        } catch (Exception e) {
            return Result.error(CodeConstants.CREATE_ERROR_NAME_OUT_OF_LEN);
        }
    }
}
