package com.zy.website.variable;

/**
 *  MQ相关常量
 **/
public class MqConstant {

    // 延时交换机
    public static final String MQ_WEBSITE_FILM_DELAY_EXCHANGE = "website_film_delay_exchange";

    // 延时队列名称
    public static final String MQ_WEBSITE_FILM_QUEUE = "website_film_delay_queue";

    // 普通交换机
    public static final String MQ_WEBSITE_NORMAL_EXCHANGE = "website_normal_exchange";

    // 普通队列名称
    public static final String MQ_WEBSITE_NORMAL_QUEUE = "website_normal_queue";

    // 普通交换机路由键
    public static final String MQ_WEBSITE_NORMAL_ROUTING_KEY = "website_normal_routing_key";

}
