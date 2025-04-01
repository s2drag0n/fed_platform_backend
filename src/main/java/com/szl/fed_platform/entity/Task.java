package com.szl.fed_platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("tasks")
@Data
public class Task {

    private Integer id;

    private Integer user_id;

    private String algorithm;

    private Integer dataset_id;

    private String params;

    private String status;

    private Timestamp created_at;

    private Timestamp start_at;

    private Timestamp completed_at;

    private String result_path;

    private String error_log;
}
