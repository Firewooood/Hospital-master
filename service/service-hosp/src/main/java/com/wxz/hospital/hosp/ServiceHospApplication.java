package com.wxz.hospital.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: WuXiangZhong
 * @Description:  http://localhost:8201/swagger-ui.html 后台测试
 * @Date: Create in 2022/8/12
 */

@EnableFeignClients(basePackages = "com.wxz.client")
@EnableDiscoveryClient          // nacos服务注册
@ComponentScan(basePackages = "com.wxz")
@SpringBootApplication
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class,args);
    }
}
