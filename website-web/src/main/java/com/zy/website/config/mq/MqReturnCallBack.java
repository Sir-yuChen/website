package com.zy.website.config.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * MQ 消息回发
 **/
@Component
public class MqReturnCallBack implements RabbitTemplate.ReturnCallback {

    private static Logger logger = LogManager.getLogger(MqConfirmCallback.class);

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            logger.info("MQ消息回发  return--message:" + new String(message.getBody(), "UTF-8") + ",replyCode:" + replyCode
                    + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
        } catch (UnsupportedEncodingException e) {
            logger.error("MQ消息回发 异常{}",e);
        }
    }
}
