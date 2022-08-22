package com.wxz.hospital.user.controller.api;

import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.user.service.MsmService;
import com.wxz.hospital.user.util.RandomUtil;
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
 * @Date: Create in 2022/8/22
 */

@RestController
@RequestMapping("api/user")
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 发送邮箱验证码
    @GetMapping("send/{email}")
    public Result sendCode(@PathVariable String email){
        // 从redis 获取验证码,如果获取到,返回ok
        // key - email  value - 验证码
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }

        // 若从redis获取不到,生成六位验证码
        code = RandomUtil.getSixBitRandom();
        System.out.println(email + "---" + code);

        // 调用service 方法,通过整合邮箱服务进行发送
        boolean isSend = msmService.send(email,code);
        // 生成验证码放入redis中,设置有效时间
        if(isSend){
            redisTemplate.opsForValue().set(email,code,2,TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.fail().message("发送邮件失败");
        }

    }
}
