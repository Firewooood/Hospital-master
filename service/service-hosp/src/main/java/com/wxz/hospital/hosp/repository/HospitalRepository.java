package com.wxz.hospital.hosp.repository;

import com.wxz.hospital.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/17
 */

@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    //判断是否存在数据   不需要写实现类, 只需命名符合mongodb规范
    Hospital getHospitalByHoscode(String hoscode);

    //根据医院名称查询
    List<Hospital> findHospitalByHosnameLike(String hosname);
}
