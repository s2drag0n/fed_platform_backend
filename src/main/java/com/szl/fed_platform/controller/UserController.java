package com.szl.fed_platform.controller;

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
    public List<User> list() {
        return userService.list();
    }

    // 添加用户
    @PostMapping
    public String addUser(@RequestBody User user) {
        return userService.save(user) ? "User added successfully" : "Failed to add user";
    }

    // 用户登录
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }
}
