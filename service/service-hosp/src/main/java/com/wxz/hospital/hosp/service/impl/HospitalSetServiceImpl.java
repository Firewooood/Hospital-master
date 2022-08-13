package com.wxz.hospital.hosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxz.hospital.hosp.mapper.HospitalSetMapper;
import com.wxz.hospital.hosp.service.HospitalSetService;
import com.wxz.hospital.model.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: WuXiangZhong
 * @Description: 默认实现了单边的CURD 以及分页查询
 * @Date: Create in 2022/8/12
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
    @Autowired
    private HospitalSetMapper hospitalSetMapper;


}
