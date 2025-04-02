package com.szl.fed_platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll() // 允许 /public/** 路径无需认证
                        .anyRequest().authenticated()             // 其他路径需要认证
                )
                .csrf(AbstractHttpConfigurer::disable); // 禁用 CSRF（开发环境下可以禁用，生产环境建议启用）

        return http.build();
    }
}
