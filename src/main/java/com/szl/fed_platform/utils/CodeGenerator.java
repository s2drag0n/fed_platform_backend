package com.szl.fed_platform.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/federated_learning?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

        FastAutoGenerator.create(url,
                        "root", "szl001023")
                // 全局配置
                .globalConfig(builder -> builder
                        .author("YourName")  // 设置作者
                        .outputDir(System.getProperty("user.dir") + "/src/main/java")  // 输出目录
                        .commentDate("yyyy-MM-dd")  // 注释日期格式
                        .disableOpenDir()  // 生成代码后不打开输出目录
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent("com.szl.fed_platform")  // 顶层包名
                        .entity("entity")  // 实体类包名
                        .mapper("mapper")  // Mapper 接口包名
                        .service("service")  // Service 接口包名
                        .serviceImpl("service.impl")  // Service 实现类包名
                        .xml("mapper.xml")  // XML 映射文件包名
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude("users", "datasets", "tasks")  // 指定需要生成的表名
                        .addTablePrefix("t_")  // 表名前缀过滤（如 t_user -> User）
                        .entityBuilder()
                        .naming(NamingStrategy.underline_to_camel)  // 数据库字段下划线转驼峰
                        .enableLombok()  // 开启 Lombok
                        .nameConvert(new CustomNameConvert())  // 自定义命名规则
                        .mapperBuilder()
                        .enableMapperAnnotation()  // 开启 @Mapper 注解
                        .serviceBuilder()
                        .formatServiceFileName("%sService")  // 自定义 Service 接口命名
                        .formatServiceImplFileName("%sServiceImpl")  // 自定义 Service 实现类命名
                )
                // 使用 Freemarker 模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 自定义命名转换规则
     */
    static class CustomNameConvert implements INameConvert {
        @Override
        public String entityNameConvert(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
            // 去掉表名中的 "s" 后缀（如 users -> User）
            String tableName = tableInfo.getName();
            if (tableName.endsWith("s")) {
                tableName = tableName.substring(0, tableName.length() - 1);  // 去掉最后一个 "s"
            }
            return NamingStrategy.capitalFirst(NamingStrategy.underlineToCamel(tableName));
        }

        @Override
        public String propertyNameConvert(com.baomidou.mybatisplus.generator.config.po.TableField field) {
            // 默认字段名转换为驼峰（无需特殊处理）
            return NamingStrategy.underlineToCamel(field.getName());
        }
    }

}
