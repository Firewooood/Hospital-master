package com.wxz.hospital.hosp.controller;

import com.wxz.hospital.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/12
 */

@RestController         // 相当于@Controller 和 @ResponseBody两个注解合并,
@RequestMapping("admin/hosp/hospitalSet")
public class HospitalSetController {
    // 注入service

    @Autowired
    HospitalSetService hospitalSetService;


}
