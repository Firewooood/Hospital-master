package com.wxz.hospital.hosp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/14
 */

@Configuration
@MapperScan("com.wxz.hospital.hosp.mapper")
public class HospConfig {
    /**
     * 分页插件
     */

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
