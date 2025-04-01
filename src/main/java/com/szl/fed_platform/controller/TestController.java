package com.szl.fed_platform.controller;

import com.szl.fed_platform.common.Result;
import com.szl.fed_platform.entity.User;
import com.szl.fed_platform.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test() {
        return "hello world111111111232222";
    }

    @GetMapping("/user_list_test")
    public List<User> user() {
        return userMapper.selectList(null);
    }

    @GetMapping("/success")
    public Result<?> success() {
        return Result.success(userMapper.selectList(null));
    }

    @GetMapping("/error")
    public Result<?> error() {
        return Result.error();
    }
}

