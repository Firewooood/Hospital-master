package com.wxz.hospital.user.controller.api;

import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.common.utils.AuthContextHolder;
import com.wxz.hospital.model.user.Patient;
import com.wxz.hospital.user.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:  就诊人管理接口
 * @Date: Create in 2022/8/23
 */

@RestController
@RequestMapping("/api/user/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    // 获取就诊人列表
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request) {
        // 当前登录用户的userid 保存在了 token中, 使用AuthContextHolder 进行获取


        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> allUserId = patientService.findAllUserId(userId);
        return Result.ok(allUserId);
    }

    // 添加就诊人
    @PostMapping("auth/save")
    public Result savePatient(@RequestBody Patient patient, HttpServletRequest request) {
        // 获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    //根据id获取就诊人信息
    @GetMapping("auth/get/{id}")
    public Result getPatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return Result.ok(patient);
    }

    //修改就诊人
    @PostMapping("auth/update")
    public Result updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.ok();
    }

    //删除就诊人
    @DeleteMapping("auth/remove/{id}")
    public Result removePatient(@PathVariable Long id) {
        patientService.removeById(id);
        return Result.ok();
    }

}