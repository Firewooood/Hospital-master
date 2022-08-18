package com.wxz.hospital.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wxz.hospital.hosp.repository.HospitalRepository;
import com.wxz.hospital.hosp.service.HospitalService;
import com.wxz.hospital.model.hosp.Hospital;
import com.wxz.hospital.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/17
 */

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;


    @Override
    public void save(Map<String, Object> paramMap) {
        // 将参数map集合转换对象  Hospital
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);

        // 判断是否存在数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist  = hospitalRepository.getHospitalByHoscode(hoscode);
        // 如果存在,则对原数据进行修改
        if(hospitalExist != null){
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else{//如果不存在，进行添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);

        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        return null;
    }

    @Override
    public void updateStatus(String id, Integer status) {

    }

    @Override
    public Map<String, Object> getHospById(String id) {
        return null;
    }

    @Override
    public String getHospName(String hoscode) {
        return null;
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return null;
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        return null;
    }
}
