package com.wxz.hospital.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxz.hospital.common.exception.HospitalException;
import com.wxz.hospital.common.helper.JwtHelper;
import com.wxz.hospital.common.result.ResultCodeEnum;
import com.wxz.hospital.enums.AuthStatusEnum;
import com.wxz.hospital.model.user.UserInfo;
import com.wxz.hospital.user.mapper.UserInfoMapper;
import com.wxz.hospital.user.service.UserInfoService;
import com.wxz.hospital.vo.user.LoginVo;
import com.wxz.hospital.vo.user.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/22
 */

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{

    @Autowired
     private RedisTemplate<String, String> redisTemplate;

    //用户邮箱登录接口
    @Override
    public Map<String, Object> login(LoginVo loginVo) {
        //从loginVo获取输入的邮箱号，和验证码
        String email = loginVo.getEmail();
        String code = loginVo.getCode();

        //判断邮箱号和验证码是否为空
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
            throw new HospitalException(ResultCodeEnum.PARAM_ERROR);
        }

        //判断邮箱验证码和输入的验证码是否一致
        String redisCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(redisCode)) {
            throw new HospitalException(ResultCodeEnum.CODE_ERROR);
        }

        //绑定邮箱号码
        UserInfo userInfo = null;


            //判断是否第一次登录：根据邮箱号查询数据库，如果不存在相同邮箱号就是第一次登录
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("email",email);
            userInfo = baseMapper.selectOne(wrapper);
            if(userInfo == null) { //第一次使用这个邮箱号登录
                //添加信息到数据库
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setEmail(email);
                userInfo.setStatus(1);
                baseMapper.insert(userInfo);
            }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new HospitalException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //不是第一次，直接登录
        //返回登录信息
        //返回登录用户名
        //返回token信息
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getEmail();
        }
        map.put("name",name);

        //jwt生成token字符串
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token",token);
        return map;
    }

    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //根据用户id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        //设置认证信息
        //认证人姓名
        userInfo.setName(userAuthVo.getName());
        //其他认证信息
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //进行信息更新
        baseMapper.updateById(userInfo);
    }
}
