package com.lan.src.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lan.src.pojo.DiskContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface DiskContentMapper extends BaseMapper<DiskContent> {
    int deleteByPrimaryKey(Integer id);

    int insert(DiskContent row);

    int insertSelective(DiskContent row);

    DiskContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiskContent row);

    int updateByPrimaryKey(DiskContent row);
}
