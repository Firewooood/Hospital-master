package com.wxz.hospital.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.order.OrderInfo;
import com.wxz.hospital.model.order.PaymentInfo;

import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/26
 */
public interface PaymentService extends IService<PaymentInfo> {
    //向支付记录表添加信息
    void savePaymentInfo(OrderInfo order, Integer status);

    //更新订单状态
    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);

}
