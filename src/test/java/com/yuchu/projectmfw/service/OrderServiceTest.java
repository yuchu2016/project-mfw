package com.yuchu.projectmfw.service;

import com.yuchu.projectmfw.ProjectMfwApplication;
import com.yuchu.projectmfw.ProjectMfwApplicationTests;
import com.yuchu.projectmfw.domain.Order;
import com.yuchu.projectmfw.domain.Passenger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectMfwApplication.class)
//@PropertySource(value = {"classpath:application.yml"})
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void saveOrder() {
        Order order = new Order();
        order.setDesCity("上海");
        order.setDepartCity("北京");
        order.setAccount("13142594193");
        order.setPassword("123456");
        order.setContactEmail("912371236@qq.com");
        order.setContactName("張三");
        order.setContactPhone("13785965823");
        order.setLevel("经济舱");
        List<Passenger> passengers = new ArrayList<>();
        Passenger passenger1 = new Passenger();
        passenger1.setName("张三");
        passenger1.setIdNum("310108196009081238");
        Passenger passenger2 = new Passenger();
        passenger2.setName("李四");
        passenger2.setIdNum("310108196009081239");
        passengers.add(passenger1);
        passengers.add(passenger2);
        order.setPassengers(passengers);
        order = orderService.saveOrder(order);
        System.out.println(order.getId());
    }

    @Test
    public void executeOrderTest() throws Exception{
//        orderService.executeOrder(1L);
    }

    @Test
    public void MQTest(){
//        orderService.push2Mq("4028969b625084bd01625084d9d30000");
    }
}