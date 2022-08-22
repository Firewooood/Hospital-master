package com.wxz.hospital.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WuXiangZhong
 * @Description: 无注册界面，第一次登录根据邮箱号判断系统是否存在，如果不存在则自动注册。微信扫描登录成功必须绑定邮箱号码，即：第一次扫描成功后绑定邮箱号，以后登录扫描直接登录成功。
 * @Date: Create in 2022/8/22
 */
@RestController
@RequestMapping("admin/user")
public class UserController {


}
