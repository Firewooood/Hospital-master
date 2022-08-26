package com.wxz.hospital.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.order.OrderInfo;
import com.wxz.hospital.vo.order.OrderCountQueryVo;
import com.wxz.hospital.vo.order.OrderQueryVo;

import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/24
 */


public interface OrderService extends IService<OrderInfo> {
    //保存订单
    Long saveOrder(String scheduleId, Long patientId);

    //根据订单id查询订单详情
    OrderInfo getOrder(String orderId);

    //订单列表（条件查询带分页）
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    //取消预约
    Boolean cancelOrder(Long orderId);

    //就诊通知
    void patientTips();

    //预约统计方法
    Map<String,Object> getCountMap(OrderCountQueryVo orderCountQueryVo);

    Object show(Long id);
}

