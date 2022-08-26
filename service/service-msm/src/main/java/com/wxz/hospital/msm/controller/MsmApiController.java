package com.wxz.hospital.msm.controller;


import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/25
 */


@RestController
@RequestMapping("/api/msm")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送邮箱
    @GetMapping("sendEmail/{email}")
    public Result sendEmail(@PathVariable String email) {
        //从redis获取验证码，如果获取获取到，返回ok
        // key 手机号  value 验证码
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //如果从redis获取不到，
        // 生成验证码，
        code = msmService.getCode();
        //调用service方法，通过整合短信服务进行发送
        //生成验证码放到redis里面，设置有效时间
        msmService.sendEmail(email,code);
        redisTemplate.opsForValue().set(email,code,2, TimeUnit.MINUTES);
        return Result.ok();
    }
}
