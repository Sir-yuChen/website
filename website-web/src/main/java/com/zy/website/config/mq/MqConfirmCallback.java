package com.zy.website.config.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * MQ 消息确认
 **/
@Component
class MqConfirmCallback implements RabbitTemplate.ConfirmCallback {
    private static Logger logger = LogManager.getLogger(MqConfirmCallback.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("ConfirmCallback消息id={}", correlationData.getId());
        if (ack) {
            logger.info("ConfirmCallback消息发送确认成功");
        } else {
            logger.info("ConfirmCallback消息发送确认失败:" + cause);
        }
    }
}