package com.wxz.hospital.hosp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxz.hospital.model.hosp.HospitalSet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resources;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/12
 */

@Repository
@Mapper
public interface HospitalSetMapper extends BaseMapper<HospitalSet> {
    // 使用mybatis plus 需继承BaseMapper,
}
