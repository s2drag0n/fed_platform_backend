package com.szl.fed_platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szl.fed_platform.entity.User;
import com.szl.fed_platform.mapper.UserMapper;
import com.szl.fed_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 用户登录
    public String login(String username, String password) {
        // 使用 QueryWrapper 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        // 根据用户名查询用户
        User user = userMapper.selectOne(queryWrapper);

        // 验证密码
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful"; // 登录成功
        }
        return "Invalid username or password"; // 登录失败
    }
}
