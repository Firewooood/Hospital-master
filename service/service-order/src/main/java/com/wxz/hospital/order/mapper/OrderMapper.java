package com.wxz.hospital.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxz.hospital.model.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/24
 */

@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
}

