package com.wxz.hospital.cmn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/15
 */

@EnableDiscoveryClient  // nacos服务注册
@SpringBootApplication
@ComponentScan(basePackages = {"com.wxz"})
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }
}
