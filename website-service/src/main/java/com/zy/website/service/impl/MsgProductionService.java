package com.zy.website.service.impl;


import cn.hutool.core.thread.ThreadUtil;
import com.zy.website.service.thread.SendMqMsgThread;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
    public void sendTimeoutMsg(String messageId, String exchange, String content, String routingKey, int delay) {
        // 通过广播模式发布延时消息，会广播至每个绑定此交换机的队列，这里的路由键没有实质性作用
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend(exchange, routingKey, content, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setMessageId(messageId);
            // 毫秒为单位，指定此消息的延时时长
            message.getMessageProperties().setDelay(delay * 1000);
            return message;
        }, correlationData);
    }

    /**
     * 优化：
     * 提高并发量
     * 提高相应速度 避免客户端长时间等待 后端异步去操作
     */
    public void sendMsgByThread(String messageId, String exchange, String content, String routingKey, int delay) {
        ThreadUtil.execute(new SendMqMsgThread(exchange, messageId, routingKey, content, delay));
    }
    /***
     *  messageId 消息ID 必传
     *  exchange 交换机
     *  routingKey key
     *  content 消息内容
     */
    // 发送普通消息
    public void sendMsg(String messageId, String exchange, String routingKey, String content) {
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend(exchange, routingKey, content, message -> {
            message.getMessageProperties().setMessageId(messageId);
            return message;
        }, correlationData);

    }
}
