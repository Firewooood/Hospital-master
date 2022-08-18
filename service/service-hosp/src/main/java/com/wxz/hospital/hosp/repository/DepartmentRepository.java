package com.wxz.hospital.hosp.repository;

import com.wxz.hospital.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/18
 */

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    // 根据depcode 获取 科室信息
    Department getDepartmentByDepcode(String depcode);

    //根据医院编号 和 科室编号查询
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
