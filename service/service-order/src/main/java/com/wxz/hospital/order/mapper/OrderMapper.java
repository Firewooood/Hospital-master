package com.wxz.hospital.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxz.hospital.model.order.OrderInfo;
import com.wxz.hospital.vo.order.OrderCountQueryVo;
import com.wxz.hospital.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/24
 */

@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(OrderCountQueryVo orderCountQueryVo);
}

