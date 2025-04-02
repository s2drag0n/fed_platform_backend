package com.szl.fed_platform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("tasks")
@Data
public class Task {

    private Integer id;

    private Integer userId;

    private String algorithm;

    private Integer datasetId;

    private String params;

    private String status;

    private Timestamp createdAt;

    private Timestamp startedAt;

    private Timestamp completedAt;

    private String resultPath;

    private String errorLog;
    
    private String taskUuid; // 新增字段
}
