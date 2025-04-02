package com.szl.fed_platform.controller;

import com.szl.fed_platform.common.Result;
import com.szl.fed_platform.entity.User;
import com.szl.fed_platform.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YourName
 * @since 2025-04-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result<List<User>> list() {
        return Result.success(userService.list());
    }

    // 添加用户
    @PostMapping("/register")
    public Result<?> addUser(@RequestBody User user) throws Exception {
        return userService.saveUserWithEncryptedPassword(user);
    }

    // 用户登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }
}
