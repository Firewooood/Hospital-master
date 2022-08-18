package com.wxz.hospital.hosp.controller.api;

import com.wxz.hospital.common.exception.HospitalException;
import com.wxz.hospital.common.helper.HttpRequestHelper;
import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.common.result.ResultCodeEnum;
import com.wxz.hospital.common.utils.MD5;
import com.wxz.hospital.hosp.service.DepartmentService;
import com.wxz.hospital.hosp.service.HospitalService;
import com.wxz.hospital.hosp.service.HospitalSetService;
import com.wxz.hospital.hosp.service.ScheduleService;
import com.wxz.hospital.hosp.utils.Verify;
import com.wxz.hospital.model.hosp.Department;
import com.wxz.hospital.model.hosp.Hospital;
import com.wxz.hospital.model.hosp.Schedule;
import com.wxz.hospital.vo.hosp.DepartmentQueryVo;
import com.wxz.hospital.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/17
 */

@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    // 上传医院接口
    @ApiOperation(value = "上传医院")
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        Verify.verify(paramMap,signKey);

        // 传输过程中"+" 转换成了" " ,将其转换回来
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData",logoData);

        hospitalService.save(paramMap);
        return Result.ok();
    }

    // 查询医院接口
    @ApiOperation(value = "查询医院")
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        Verify.verify(paramMap,signKey);

        // 调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    // 上传科室接口
    @ApiOperation(value = "上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);


        departmentService.save(paramMap);
        return Result.ok();

    }

    // 获取科室分页列表
    @ApiOperation(value = "获取科室分页列表")
    @PostMapping("department/list")
    public Result departmentList(HttpServletRequest request){
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        Verify.verify(paramMap,signKey);
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        Page<Department> pageDepartment = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageDepartment);
    }

    // 删除科室
    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        Verify.verify(paramMap,signKey);
        String depcode = (String) paramMap.get("depcode");
        if(StringUtils.isEmpty(hoscode)){
            throw new HospitalException(ResultCodeEnum.PARAM_ERROR);
        }

        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    // 上传排班
    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 签名校验
        Verify.verify(paramMap,signKey);

        scheduleService.save(paramMap);
        return Result.ok();
    }

    // 删除排班
    @ApiOperation(value = "删除排班")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 签名校验
        Verify.verify(paramMap,signKey);

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    // 获取排班分页列表
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        //科室编号
        String depcode = (String)paramMap.get("depcode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 签名校验
        Verify.verify(paramMap,signKey);

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }
}
