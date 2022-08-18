package com.wxz.hospital.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wxz.hospital.hosp.repository.DepartmentRepository;
import com.wxz.hospital.hosp.service.DepartmentService;
import com.wxz.hospital.model.hosp.Department;
import com.wxz.hospital.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/18
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    // 保存科室信息
    @Override
    public void save(Map<String, Object> paramMap) {
        // 将参数map集合转换成对象 Department
        Department department = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Department.class);

        // 判断是否存在数据
        String depcode = department.getDepcode();
        Department departmentExist = departmentRepository.
                getDepartmentByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());
        //判断
        if(departmentExist!=null) {
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        // 创建一个Pageable 对象, 设置当前页和每页记录数,  0 为第一页
        Pageable pageable = PageRequest.of(page-1,limit);
        // 创建Example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);

        // 构建匹配器
        ExampleMatcher matcher = ExampleMatcher.matching() // 构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 改变默认字符串匹配形式: 模糊查询
                .withIgnoreCase(true);  // 忽略大小写
        Example<Department> example = Example.of(department,matcher);

        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        departmentRepository.deleteById(department.getId());
    }
}
