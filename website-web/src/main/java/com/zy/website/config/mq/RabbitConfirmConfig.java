package com.zy.website.config.mq;

import com.zy.website.controller.FilmController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author zhangyu
 * RabbitMQ的配置 消息发送到 exchange，queue 的回调函数
 **/
@Configuration
public class RabbitConfirmConfig {

    private static Logger logger = LogManager.getLogger(FilmController.class);


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        /*设置开启Mandatory才能触发回调函数，无论消息推送结果怎么样都强制调用回调函数*/
        rabbitTemplate.setMandatory(true);

        /*消息发送到Exchange的回调，无论成功与否*/
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            logger.info("ConfirmCallback：" + "相关数据：" + correlationData);
            logger.info("ConfirmCallback：" + "确认情况：" + ack);
            logger.info("ConfirmCallback：" + "原因：" + cause);
        });

        /*消息从Exchange路由到Queue失败的回调*/
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            logger.info("ReturnCallback：" + "消息：" + message);
            logger.info("ReturnCallback：" + "回应码：" + replyCode);
            logger.info("ReturnCallback：" + "回应信息：" + replyText);
            logger.info("ReturnCallback：" + "交换机：" + exchange);
            logger.info("ReturnCallback：" + "路由键：" + routingKey);
        });
        return rabbitTemplate;
    }
}
