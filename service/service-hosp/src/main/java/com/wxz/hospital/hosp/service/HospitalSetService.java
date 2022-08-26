package com.wxz.hospital.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.hosp.HospitalSet;
import com.wxz.hospital.vo.order.SignInfoVo;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/12
 */
public interface HospitalSetService extends IService<HospitalSet> {

    // 根据传递过来的医院编码,查询mysql 数据库, 得到签名
    String getSignKey(String hoscode);

    // 获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
