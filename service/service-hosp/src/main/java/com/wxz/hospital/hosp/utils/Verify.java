package com.wxz.hospital.hosp.utils;

import com.wxz.hospital.common.exception.HospitalException;
import com.wxz.hospital.common.result.ResultCodeEnum;
import com.wxz.hospital.common.utils.MD5;

import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/18
 */
public class Verify {
    public static void verify(Map<String, Object> paramMap, String signKey){
        // 1. 获取医院系统传递过来的签名,签名进行了MD5加密
        String hospSign = (String)paramMap.get("sign");

        // 2. 根据传递过来的医院hoscode 查询数据库,查询签名
        String hoscode = (String)paramMap.get("hoscode");

        // 3. 将数据库查询出的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4. 判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new HospitalException(ResultCodeEnum.SIGN_ERROR);
        }

    }

}
