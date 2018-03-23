package com.yuchu.projectmfw.mq.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 10:01
 */
@Component
public class OrderSender {

    private static final Logger log = LoggerFactory.getLogger(OrderSender.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String orderId) {
        String context = orderId;
        log.info("Send to MQ,orderId:{}",context);
        this.rabbitTemplate.convertAndSend("order", context);
    }

}
