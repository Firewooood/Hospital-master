package com.wxz.hospital.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wxz.hospital.common.exception.HospitalException;
import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.hosp.service.HospitalSetService;
import com.wxz.hospital.model.hosp.HospitalSet;
import com.wxz.hospital.vo.hosp.HospitalSetQueryVo;
import com.wxz.hospital.common.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Random;

/**
 * @Author: WuXiangZhong
 * @Description: 医院设置管理
 * @Date: Create in 2022/8/12
 */

@CrossOrigin        // 跨域处理
@Api(tags = "医院设置管理")
@RestController         // 相当于@Controller 和 @ResponseBody两个注解合并,
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    // 注入service

    @Autowired
    private HospitalSetService hospitalSetService;

    // 1. 查询医院设置表所有信息   http://localhost:8201/admin/hosp/hospitalSet/findAll
    @ApiOperation("获取所有医院设置")
    @GetMapping("/findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //2 逻辑删除医院设置
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 3. 条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet (@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody(required = false)HospitalSetQueryVo hospitalSetQueryVo){    // 此处使用VO 对象 代替 DTO 接收前端的查询值
        // 创建page对象,传递当前页,每页记录数
        Page<HospitalSet> page = new Page<>(current,limit);
        // 构建条件
        // QueryWrapper 是mybatis plus 实现查询的对象封装操作类
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if(!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hospitalSetQueryVo.getHosname());   // 此处第一个参数 column 需要为数据库中的真实字段
        }
        if(!StringUtils.isEmpty(hoscode)){
            wrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }
        // 调用方法实现分页查询
        IPage<HospitalSet> pageHospitalSet = hospitalSetService.page(page,wrapper);

        return Result.ok(pageHospitalSet);
    }


    // 4. 添加医院设置
    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        // 设置状态 1 使用, 0 不能使用
        hospitalSet.setStatus(1);
        // 签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        // 调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return Result.ok();
        }else return Result.fail();
    }

    // 5.根据id获取医院设置
    @ApiOperation(value = "根据id获取医院设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id){
        try {
//            int a = 1/0;
        } catch (Exception e) {
            throw new HospitalException("失败",201);
        }
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }


    // 6. 修改医院设置
    @ApiOperation(value = "修改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag){
            return Result.ok();
        }else return Result.fail();
    }

    //7 批量删除医院设置
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 医院设置锁定和解锁
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //9 发送签名秘钥
    @ApiOperation(value = "发送签名秘钥")
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }
}
