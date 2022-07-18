package com.zy.website.config.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ的配置 消息发送到 exchange，queue 的回调函数
 **/
@Configuration
public class RabbitConfirmConfig {

    private static Logger logger = LogManager.getLogger(RabbitConfirmConfig.class);

    /** ======================== 定制一些处理策略 =============================*/
    /**
     * 定制化amqp模版
     * <p>
     * Rabbit MQ的消息确认有两种。
     * <p>
     * 一种是消息发送确认：这种是用来确认生产者将消息发送给交换机，交换机传递给队列过程中，消息是否成功投递。
     * 发送确认分两步：一是确认是否到达交换机，二是确认是否到达队列
     * <p>
     * 第二种是消费接收确认：这种是确认消费者是否成功消费了队列中的消息。
     * Springboot中使用ConfirmCallback和ReturnCallback
     * 注意：
     *  在需要使用消息的return机制时候，mandatory参数必须设置为true
     * 新版本开启消息的confirm配置publisher-confirms已经过时，改为使用publisher-confirm-type参数设置（correlated:开启;NONE:关闭）
     */
/*
     //TODO-zy MQ回调方式一 通过配置
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
//        设置开启Mandatory才能触发回调函数，无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        *//**
         * 使用该功能需要开启消息确认 无论成功与否，需要配置 publisher-confirms: true
         * 通过实现ConfirmCallBack接口，用于实现消息发送到交换机Exchange后接收ack回调
         * correlationData  消息唯一标志
         * ack              确认结果
         * cause            失败原因
         *//*
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            logger.warn("ConfirmCallback：" + "消息唯一标志={}",correlationData);
            logger.warn("ConfirmCallback：" + "确认情况={}",ack);
            logger.error("ConfirmCallback：" + "原因={}", cause);
        });
        *//**
         * 消息从Exchange路由到Queue失败的回调
         * 使用该功能需要开启消息返回确认，需要配置 publisher-returns: true
         * 通过实现ReturnCallback接口，如果消息从交换机发送到对应队列失败时触发
         * message    消息主体 message
         * replyCode  消息主体 message
         * replyText  描述
         * exchange   消息使用的交换机
         * routingKey 消息使用的路由键
         *//*
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            logger.info("ReturnCallback：" + "消息：" + message);
            logger.info("ReturnCallback：" + "回应码：" + replyCode);
            logger.info("ReturnCallback：" + "回应信息：" + replyText);
            logger.info("ReturnCallback：" + "交换机：" + exchange);
            logger.info("ReturnCallback：" + "路由键：" + routingKey);
        });
        return rabbitTemplate;
    }*/
}
