package com.zy.website.service.impl;


import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 发送消息到MQ
 **/
@Service("msgProductionService")
public class MsgProductionService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    // 发送延时信息
    public void sendTimeoutMsg(String exchange,String content, String routingKey, int delay) {
        // 通过广播模式发布延时消息，会广播至每个绑定此交换机的队列，这里的路由键没有实质性作用
        rabbitTemplate.convertAndSend(exchange, routingKey, content, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            // 毫秒为单位，指定此消息的延时时长
            message.getMessageProperties().setDelay(delay * 1000);
            return message;
        });
    }

    // 发送普通消息
    public void sendMsg(String exchange,String routingKey, String content) {
        // DirectExchange类型的交换机，必须指定对应的路由键
        rabbitTemplate.convertAndSend(exchange, routingKey, content);
    }

}
