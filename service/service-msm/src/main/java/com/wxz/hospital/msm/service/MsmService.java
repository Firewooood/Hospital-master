package com.wxz.hospital.msm.service;

import com.wxz.hospital.vo.msm.MsmVo;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/25
 */
public interface MsmService {

    //发送邮箱 验证码
    boolean sendEmail(String email, String code);



    //生成验证码
    String getCode();

    //mq使用发送短信
    boolean sendEmail(MsmVo msmVo);

}
