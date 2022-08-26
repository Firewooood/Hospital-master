package com.wxz.hospital.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxz.hospital.model.order.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/26
 */

@Mapper
public interface PaymentMapper extends BaseMapper<PaymentInfo> {
}
