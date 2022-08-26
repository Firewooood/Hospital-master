package com.wxz.hospital.msm.service.impl;

import com.wxz.hospital.msm.service.MsmService;
import com.wxz.hospital.msm.utils.RandomUtil;
import com.wxz.hospital.vo.msm.MsmVo;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/25
 */

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean sendEmail(String _email, String code) {
        // 判断邮箱号是否为空
        if(StringUtils.isEmpty(_email))return false;

        try {
            HtmlEmail email = new HtmlEmail();
            // 设置qq邮箱服务器
            email.setHostName("smtp.qq.com");
            email.setCharset("utf-8");
            email.addTo(_email);
            // 发件人
            email.setFrom("374004913@qq.com", "吴祥中");
            // 发件人, 以及发件人对应的授权码
            email.setAuthentication("374004913@qq.com","ibfgvrjnlgdwbheh");
            // 发送主体
            email.setSubject("邮箱验证码");
            email.setMsg("请注意查收您的支付验证码"+ code);
            email.send();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getCode() {
        return RandomUtil.getSixBitRandom();
    }

    @Override
    public boolean sendEmail(MsmVo msmVo) {
        if(!StringUtils.isEmpty(msmVo.getEmail())) {
            String code = getCode();
            return this.sendEmail(msmVo.getEmail(),code);
        }
        return false;
    }

}
