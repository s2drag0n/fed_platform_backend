package com.szl.fed_platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("users")
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private Timestamp created_at;

    private Timestamp updated_at;

}
