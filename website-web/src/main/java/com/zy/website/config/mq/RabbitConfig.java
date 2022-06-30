package com.zy.website.config.mq;

import com.zy.website.facade.variable.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ的配置 主要是队列，交换机的配置绑定
 **/
@Configuration
public class RabbitConfig {

    //https://blog.csdn.net/weixin_38192427/article/details/120509586
    /**
     * durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
     * exclusive 表示该消息队列是否只在当前connection生效,默认是false
     * auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     */
    // 延时队列
    @Bean
    public Queue delayQueue() {
        return new Queue(MqConstant.MQ_WEBSITE_FILM_QUEUE, true,false,false);
    }

    /**
     * 交换机说明:
     * durable="true" rabbitmq重启的时候不需要创建新的交换机
     * auto-delete 表示交换机没有在使用时将被自动删除 默认是false
     * direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     * topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     * fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     */
    // 延时交换机 设置
    public FanoutExchange delayExchange() {
        //使用自定义交换器
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        FanoutExchange fanoutExchange = new FanoutExchange(MqConstant.MQ_WEBSITE_FILM_DELAY_EXCHANGE, true, false, args);
        fanoutExchange.setDelayed(true);
        return fanoutExchange;
    }

    // 绑定延时队列与延时交换机
    @Bean
    public Binding delayBind() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange());
    }

    // ------------------------普通队列------------------------
    // 普通队列
    @Bean
    public Queue normalQueue() {
        return new Queue(MqConstant.MQ_WEBSITE_NORMAL_QUEUE, true);
    }

    // 普通交换机
    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(MqConstant.MQ_WEBSITE_NORMAL_EXCHANGE, true, false);
    }

    // 绑定普通消息队列
    @Bean
    public Binding normalBind() {
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(MqConstant.MQ_WEBSITE_NORMAL_ROUTING_KEY);
    }

    // 定义消息转换器
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


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
     */
    // 定义消息模板用于发布消息，并且设置其消息转换器
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setConfirmCallback(new MqConfirmCallback());//TODO-zy MQ回调方式二 单独实现 更加灵活
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new MqReturnCallBack());//消息回发
        //rabbitTemplate.setChannelTransacted(true);//开启mq事务 生产勿用
        return rabbitTemplate;
    }
    /**
     * 实现多线程处理队列消息
     * @RabbitListener默认是单线程监听队列
     * 当线程消费消息容易引起消息处理缓慢，消息堆积，不能最大化利用硬件资源
     * 可以通过配置mq工厂参数，增加并发量处理数据即可实现多线程处理监听队列，实现多线程处理消息
     */
    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(10);
        configurer.configure(factory,connectionFactory);
        return factory;
    }
    // --------------------------使用RabbitAdmin启动服务便创建交换机和队列--------------------------
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        // 创建延时交换机和对列
        rabbitAdmin.declareExchange(delayExchange());
        rabbitAdmin.declareQueue(delayQueue());
        // 创建普通交换机和对列
        rabbitAdmin.declareExchange(normalExchange());
        rabbitAdmin.declareQueue(normalQueue());
        return new RabbitAdmin(connectionFactory);
    }

}

