package com.wxz.hospital.order.controller;

import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.order.service.PaymentService;
import com.wxz.hospital.order.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/26
 */

@RestController
@RequestMapping("/api/order/weixin")
public class WeixinController {
    @Autowired
    private WeixinService weixinService;

    @Autowired
    private PaymentService paymentService;

    // 生成微信二维码
    @GetMapping("createNative/{orderId}")
    public Result createNative(@PathVariable Long orderId){
        Map map = weixinService.createNative(orderId);
        return Result.ok(map);
    }

    // 查询支付状态
    @GetMapping("queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable Long orderId) {
        // 调用微信接口实现支付状态查询
        Map<String, String> resultMap = weixinService.queryPayStatus(orderId);

        if(resultMap == null){
            return Result.fail().message("支付出错");
        }
        if("SUCCESS".equals(resultMap.get("trade_state"))){ // 支付成功
            // 更新订单状态
            String s = resultMap.get(("out_trade_no")); //对外业务编号
            paymentService.paySuccess(s,resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");
    }
}
