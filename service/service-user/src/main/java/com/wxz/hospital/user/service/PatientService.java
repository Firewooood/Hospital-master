package com.wxz.hospital.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.user.Patient;

import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/23
 */
public interface PatientService extends IService<Patient> {
    //获取就诊人列表
    List<Patient> findAllUserId(Long userId);
    //根据id获取就诊人信息
    Patient getPatientById(Long id);
}
