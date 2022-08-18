package com.wxz.hospital.hosp.controller.api;

import com.wxz.hospital.common.exception.HospitalException;
import com.wxz.hospital.common.helper.HttpRequestHelper;
import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.common.result.ResultCodeEnum;
import com.wxz.hospital.common.utils.MD5;
import com.wxz.hospital.hosp.service.HospitalService;
import com.wxz.hospital.hosp.service.HospitalSetService;
import com.wxz.hospital.model.hosp.Hospital;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 1. 获取医院系统传递过来的签名,签名进行了MD5加密
        String hospitalSign = (String)paramMap.get("sign");

        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 3. 将数据库查询出的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4. 判断签名是否一致
        if(!hospitalSign.equals(signKeyMd5)){
            throw new HospitalException(ResultCodeEnum.SIGN_ERROR);
        }
        // 传输过程中"+" 转换成了" " ,将其转换回来
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData",logoData);

        hospitalService.save(paramMap);
        return Result.ok();
    }

    // 查询医院接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 1. 获取医院系统传递过来的签名,签名进行了MD5加密
        String hospitalSign = (String)paramMap.get("sign");

        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 3. 将数据库查询出的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4. 判断签名是否一致
        if(!hospitalSign.equals(signKeyMd5)){
            throw new HospitalException(ResultCodeEnum.SIGN_ERROR);
        }

        // 调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }
}
