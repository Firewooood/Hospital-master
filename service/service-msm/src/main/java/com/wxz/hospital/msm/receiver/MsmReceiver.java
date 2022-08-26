package com.wxz.hospital.msm.receiver;

import com.rabbitmq.client.Channel;
import com.wxz.common.rabbit.constant.MqConst;
import com.wxz.hospital.msm.service.MsmService;
import com.wxz.hospital.vo.msm.MsmVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/25
 */

@Component("rabbitConsumer")
public class MsmReceiver {
    @Autowired
    private MsmService msmService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void sendEmail(MsmVo msmVo, Message message, Channel channel) {
        msmService.sendEmail(msmVo);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_ORDER),
            key = {MqConst.ROUTING_ORDER}
    ))
    public void send1(MsmVo msmVo, Message message, Channel channel) {
        msmService.sendEmail(msmVo);
    }
}
