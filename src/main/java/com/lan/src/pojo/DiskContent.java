package com.lan.src.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 盘块类（后端）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("disk_content")
public class DiskContent{
    private Integer id;

    private Integer status;

    private String content;
}
