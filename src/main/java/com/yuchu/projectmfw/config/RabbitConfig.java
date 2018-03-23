package com.yuchu.projectmfw.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 10:10
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue orderQueue(){
        return new Queue("order");
    }
}
