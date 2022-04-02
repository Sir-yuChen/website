package com.zy.website.test.mq;

import com.zy.website.service.impl.MsgProductionService;
import com.zy.website.test.BaseTest;
import com.zy.website.utils.UUIDGenerator;
import com.zy.website.variable.MqConstant;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class MqControllerTest extends BaseTest {

    @Resource
    private MsgProductionService msgProductionService;

    @Test
    public void sendMsg() {
        // 发送多个延时消息40s
  /*      msgProductionService.sendTimeoutMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_FILM_DELAY_EXCHANGE, "hello1", "routingKey1", 30);
        msgProductionService.sendTimeoutMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_FILM_DELAY_EXCHANGE, "hello2", "routingKey2", 60);
        msgProductionService.sendTimeoutMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_FILM_DELAY_EXCHANGE, "hello3", "routingKey3", 90);
*/
        // 发送普通消息
        msgProductionService.sendMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_NORMAL_EXCHANGE, MqConstant.MQ_WEBSITE_NORMAL_ROUTING_KEY, "你好");
        msgProductionService.sendMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_NORMAL_EXCHANGE, MqConstant.MQ_WEBSITE_NORMAL_ROUTING_KEY, "欢迎");
        msgProductionService.sendMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_NORMAL_EXCHANGE,"test", "欢迎");
        System.out.println("向MQ发送了消息");
    }


    public static void main(String[] args) {
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal decimal = zero.add(new BigDecimal(5000));
        System.out.println("bigDecimal = " + decimal);
    }





}
