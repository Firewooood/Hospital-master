package com.wxz.hospital.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxz.hospital.model.user.Patient;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/23
 */

@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
