package com.zy.website.config.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: 消息发送到交换机确认机制
 */
@Component
public class MsgSendConfirmCallback implements RabbitTemplate.ConfirmCallback {

    private static Logger logger = LogManager.getLogger(MsgSendConfirmCallback.class);

    /**
     * 使用该功能需要开启消息确认，配置文件需要需要配置 publisher-confirms: true
     * correlationData  消息唯一标志
     * ack              确认结果
     * cause            失败原因
     * <p>
     * PS：通过实现ConfirmCallBack接口，用于实现消息发送到交换机Exchange后接收ack回调
     * </p>
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.debug("消息发送到exchange成功,id: {}", correlationData.getId());
        } else {
            logger.debug("消息{}发送到exchange失败,原因: {}", correlationData.getId(), cause);
        }
    }
}