package com.szl.fed_platform.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 加密密码
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // 验证密码
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
