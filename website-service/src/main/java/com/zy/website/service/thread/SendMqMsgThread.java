package com.zy.website.service.thread;

import com.zy.website.service.impl.FilmServiceImpl;
import com.zy.website.utils.spring.ApplicationContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 *  多线程发送消息
 * @Author zhangyu
 * @Date 16:03 2022/3/22
 **/
public class SendMqMsgThread implements Runnable{

    private static Logger logger = LogManager.getLogger(FilmServiceImpl.class);

    private String exchange;
    private String messageId;
    private String routingKey;
    private String content;
    private int delay;


    public SendMqMsgThread(String exchange,String messageId,String routingKey,String content,int delay){
        this.exchange = exchange;
        this.messageId = messageId;
        this.routingKey = routingKey;
        this.content = content;
        this.delay = delay;
    }

    @Override
    public void run() {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(System.currentTimeMillis());
        RabbitTemplate rabbitTemplate = ApplicationContextUtil.getBean(RabbitTemplate.class);
        // 通过广播模式发布延时消息，会广播至每个绑定此交换机的队列，这里的路由键没有实质性作用
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend(exchange, routingKey, content, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setMessageId(messageId);
            // 毫秒为单位，指定此消息的延时时长
            message.getMessageProperties().setDelay(delay * 1000);
            return message;
        }, correlationData);



        //rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_ORDER_EXCHANGE, "", msg);
        logger.info("多线程发送MQ消息 当前线程{} 耗时{}ms",Thread.currentThread().getName(),System.currentTimeMillis() - (Long)threadLocal.get());
    }
}
