package com.wxz.client.user;

import com.wxz.hospital.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/24
 */
@FeignClient(value = "service-user", path = "/api/user/patient/")
@Repository
public interface PatientFeignClient {

    //根据就诊人id获取就诊人信息
    @GetMapping("inner/get/{id}")
    Patient getPatientOrder(@PathVariable("id") Long id);



}