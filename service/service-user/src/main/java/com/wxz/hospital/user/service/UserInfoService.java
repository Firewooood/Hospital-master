package com.wxz.hospital.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.user.UserInfo;
import com.wxz.hospital.vo.user.LoginVo;
import com.wxz.hospital.vo.user.UserAuthVo;

import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/22
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> login(LoginVo loginVo);

    void userAuth(Long userId, UserAuthVo userAuthVo);
}
