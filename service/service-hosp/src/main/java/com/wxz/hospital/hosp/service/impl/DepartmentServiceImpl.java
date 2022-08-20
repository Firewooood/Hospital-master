package com.wxz.hospital.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wxz.hospital.hosp.repository.DepartmentRepository;
import com.wxz.hospital.hosp.service.DepartmentService;
import com.wxz.hospital.model.hosp.Department;
import com.wxz.hospital.vo.hosp.DepartmentQueryVo;
import com.wxz.hospital.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<DepartmentVo> findDepartmentTree(String hoscode) {
        // 创建List集合,最终用于数据封装
        List<DepartmentVo> result = new ArrayList<>();

        // 根据医院编号,查询医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example<Department> example = Example.of(departmentQuery);
        List<Department> departmentList = departmentRepository.findAll(example);

        // 根据大科室编号, bigcode 分组, 获取每个大科室的子科室
        Map<String, List<Department>> departmentMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        // 遍历departmentMap
        for(Map.Entry<String,List<Department>> entry : departmentMap.entrySet()){
            String bigcode = entry.getKey();
            List<Department> departmentList1 = entry.getValue();
            // 封装大科室
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bigcode);
            departmentVo.setDepname(departmentList1.get(0).getBigname());

            // 封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for(Department department: departmentList1){
                DepartmentVo departmentVo2 = new DepartmentVo();
                departmentVo2.setDepname(department.getDepname());
                departmentVo2.setDepcode(department.getDepcode());
                children.add(departmentVo2);
            }
            // 将小科室list结合放到大科室children里面
            departmentVo.setChildren(children);
            result.add(departmentVo);
        }
        return result;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
    }
}
