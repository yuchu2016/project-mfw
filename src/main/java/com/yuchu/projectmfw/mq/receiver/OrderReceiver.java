package com.yuchu.projectmfw.mq.receiver;

import com.yuchu.projectmfw.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 10:05
 */
@Component
@RabbitListener(queues = "order")
public class OrderReceiver {
    private static final Logger log = LoggerFactory.getLogger(OrderReceiver.class);
    @Autowired
    private OrderService orderService;
    @RabbitHandler
    public void execute(String orderId) {
       log.info("Receive from MQ,orderId:{} ",orderId);
       //todo
        try {
            orderService.executeOrder(orderId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
