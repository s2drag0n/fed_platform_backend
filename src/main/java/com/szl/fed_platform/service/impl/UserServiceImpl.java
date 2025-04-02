package com.szl.fed_platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szl.fed_platform.common.Result;
import com.szl.fed_platform.entity.User;
import com.szl.fed_platform.mapper.UserMapper;
import com.szl.fed_platform.service.UserService;
import com.szl.fed_platform.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    UserMapper userMapper;

    PasswordUtil passwordUtil;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordUtil = new PasswordUtil();
    }

    // 用户注册（带密码加密）
    @Transactional
    public Result<?> saveUserWithEncryptedPassword(User user) throws Exception {
        try {
            String encryptedPassword = passwordUtil.encodePassword(user.getPassword());
            user.setPassword(encryptedPassword);

            int row = userMapper.insert(user);
            if (row > 0) {
                return Result.success();
            } else {
                return Result.error("注册失败，请更换用户名重试。");
            }
        } catch (Exception e) {
            log.error("Unexpected error: {}");
            return Result.error("注册失败，请更换用户名重试。");
        }
    }

    // 用户登录
    @Transactional
    public Result<?> login(String username, String password) {
        // 使用 QueryWrapper 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        // 根据用户名查询用户
        User user = userMapper.selectOne(queryWrapper);


        if (user != null && passwordUtil.matches(password, user.getPassword())) {
            return Result.success();
        }

        return Result.error("登录失败，账户名不存在或密码不符。");
    }
}
