package com.wxz.hospital.hosp.service;

import com.wxz.hospital.model.hosp.Department;
import com.wxz.hospital.vo.hosp.DepartmentQueryVo;
import com.wxz.hospital.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/18
 */
public interface DepartmentService {

    void save(Map<String, Object> paramMap);

    // 查询科室信息
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);

    List<DepartmentVo>  findDepartmentTree(String hoscode);

    String getDepName(String hoscode, String depcode);

    Department getDepartment(String hoscode, String depcode);
}
