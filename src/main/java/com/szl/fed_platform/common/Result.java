package com.szl.fed_platform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;

    private String msg;

    private Object data;

    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> success(Object data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error() {
        return new Result<>(500, "系统错误，请联系管理员", null);
    }
}
