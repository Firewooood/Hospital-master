package com.wxz.hospital.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/24
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.wxz")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wxz.client")
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
