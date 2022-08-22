package com.wxz.hospital.hosp.controller;

import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.hosp.service.HospitalService;
import com.wxz.hospital.model.hosp.Hospital;
import com.wxz.hospital.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/19
 */

@Api(tags = "医院列表子模块")
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    // 医院列表(条件查询带分页)
    @ApiOperation("医院列表(条件查询带分页)")
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable int page, @PathVariable int limit, HospitalQueryVo hospitalQueryVo){   //HospitalQueryVo中包含省, 市, 医院名称 三个信息
        Page<Hospital> hospitals = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }

    //更新医院上线状态
    @ApiOperation(value = "更新医院上线状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,@PathVariable Integer status) {
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    //医院详情信息
    @ApiOperation(value = "医院详情信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id) {
        Map<String,Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }

}
