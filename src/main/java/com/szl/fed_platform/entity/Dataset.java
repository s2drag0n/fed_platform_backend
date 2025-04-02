package com.szl.fed_platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("datasets")
@Data
public class Dataset {

    private Integer id;

    private Integer userId;

    private String name;

    private String path;

    private boolean isPublic;

    private Timestamp createdAt;

    private String description;
}
