package com.szl.fed_platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("datasets")
@Data
public class Dataset {

    private Integer id;

    private Integer user_id;

    private String name;

    private String path;

    private boolean is_public;

    private Timestamp created_at;

    private String description;
}
