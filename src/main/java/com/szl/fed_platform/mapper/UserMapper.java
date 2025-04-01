package com.szl.fed_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szl.fed_platform.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
