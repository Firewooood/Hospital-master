package com.wxz.hospital.user.controller.api;

import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.common.utils.AuthContextHolder;
import com.wxz.hospital.model.user.UserInfo;
import com.wxz.hospital.user.service.UserInfoService;
import com.wxz.hospital.vo.user.LoginVo;
import com.wxz.hospital.vo.user.UserAuthVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:    无注册界面，第一次登录根据邮箱号判断系统是否存在，如果不存在则自动注册。微信扫描登录成功必须绑定邮箱号码，即：第一次扫描成功后绑定邮箱号，以后登录扫描直接登录成功。
 * @Date: Create in 2022/8/22
 */

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        Map<String, Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    //用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }


    //获取用户id信息接口
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);
    }
}
