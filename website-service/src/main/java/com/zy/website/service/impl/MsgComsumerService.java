package com.zy.website.service.impl;

import com.rabbitmq.client.Channel;
import com.zy.website.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 *  MQ 消费者
 **/
@Service("msgComsumerService")
public class MsgComsumerService {

    private static Logger logger = LogManager.getLogger(MsgComsumerService.class);

    @Resource
    FilmService filmService;

    // 监听消费延时消息
    @RabbitListener(queues = {"website_film_delay_queue"})
    @RabbitHandler
    public void process(String content, Message message, Channel channel) throws IOException {
        try {
            // 消息的可定确认，第二个参数如果为true将一次性确认所有小于deliveryTag的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //调用方法消费消息
            filmService.getFilmInfoByExternalApi(content);
            logger.info("延迟队列消息[{}]被消费！！",content);
        } catch (Exception e) {
            logger.error("延迟队列消息 处理失败:{}", e.getMessage());
            // 直接拒绝消费该消息，后面的参数一定要是false，否则会重新进入业务队列，不会进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            /*// ack返回false，requeue-true并重新回到队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);*/
        }
    }

    // 消费普通消息
    @RabbitListener(queues = {"website_normal_queue"})
    @RabbitHandler
    public void process1(String content, Message message, Channel channel) throws IOException {
        try {
            logger.info("普通队列的内容[{}]", content);
            // 消息的可定确认，第二个参数如果为true将一次性确认所有小于deliveryTag的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.info("普通信息处理完毕");
        } catch (Exception e) {
            logger.error("处理失败:{}", e.getMessage());
            // 直接拒绝消费该消息，后面的参数一定要是false，否则会重新进入业务队列，不会进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }


}
