package com.wxz.hospital.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.order.PaymentInfo;
import com.wxz.hospital.model.order.RefundInfo;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/26
 */
public interface RefundInfoService  extends IService<RefundInfo> {

    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
