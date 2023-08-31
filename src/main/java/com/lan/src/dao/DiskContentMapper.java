package com.lan.src.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lan.src.pojo.DiskContent;

public interface DiskContentMapper extends BaseMapper<DiskContent> {
    int deleteByPrimaryKey(Integer id);

    int insert(DiskContent row);

    int insertSelective(DiskContent row);

    DiskContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiskContent row);

    int updateByPrimaryKey(DiskContent row);
}
