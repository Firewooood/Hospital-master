package com.wxz.hospital.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/22
 */
@Configuration
@MapperScan("com.wxz.hospital.user.mapper")
public class UserConfig {
}
