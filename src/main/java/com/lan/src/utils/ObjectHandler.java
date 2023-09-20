package com.lan.src.utils;

import com.lan.src.dao.DiskContentMapper;
import com.lan.src.dao.FileInfoMapper;
import com.lan.src.pojo.DiskContent;
import com.lan.src.pojo.FileInfo;

/**
 * 部分对象的处理类
 */
public class ObjectHandler {
    /**
     * 处理打开文件表的指针部分
     * @param fileInfo 待处理对象
     * @return 处理结果
     */
    public static void handlePoint(FileInfo fileInfo, DiskContentMapper diskContentMapper){
        Integer startId = fileInfo.getStartId();
        Integer size = fileInfo.getSize();
        fileInfo.setReadDnum(startId);                  //读盘块号
        fileInfo.setReadBnum(0);                        //读指针位置
        DiskContent temp = diskContentMapper.selectByPrimaryKey(startId);
        Integer status = temp.getStatus();
        while(status!=-1){                              //搜寻下一个盘块号
            temp = diskContentMapper.selectByPrimaryKey(status);
            status = temp.getStatus();
        }
        fileInfo.setWriteDnum(temp.getId());                     //写盘块号
        fileInfo.setWriteBnum(temp.getContent().length()); //写指针位置
    }
}
