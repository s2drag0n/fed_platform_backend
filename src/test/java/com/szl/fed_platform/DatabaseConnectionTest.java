package com.szl.fed_platform;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/federated_learning?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "szl001023";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("数据库连接成功！");
        } catch (Exception e) {
            System.err.println("数据库连接失败：" + e.getMessage());
        }
    }
}
