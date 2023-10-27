package com.lan.src.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lan.src.pojo.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo row);

    int insertSelective(FileInfo row);

    FileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileInfo row);

    int updateByPrimaryKey(FileInfo row);

    FileInfo selectByStartId(Integer startId);
}
