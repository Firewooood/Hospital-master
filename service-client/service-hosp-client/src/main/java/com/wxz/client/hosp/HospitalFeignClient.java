package com.wxz.client.hosp;

import com.wxz.hospital.vo.hosp.ScheduleOrderVo;
import com.wxz.hospital.vo.order.SignInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/24
 */
@FeignClient(value = "service-hosp")
@Repository
public interface HospitalFeignClient {
    /**
     * 根据排班id获取预约下单数据
     */
    @GetMapping("/api/hosp/hospital/inner/getScheduleOrderVo/{scheduleId}")
    ScheduleOrderVo getScheduleOrderVo(@PathVariable("scheduleId") String scheduleId);

    /**
     * 获取医院签名信息
     */
    @GetMapping("/api/hosp/hospital/inner/getSignInfoVo/{hoscode}")
    SignInfoVo getSignInfoVo(@PathVariable("hoscode") String hoscode);

}

