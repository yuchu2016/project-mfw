package com.yuchu.projectmfw.controller;

import com.yuchu.projectmfw.common.Result;
import com.yuchu.projectmfw.domain.Order;
import com.yuchu.projectmfw.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.yuchu.projectmfw.common.ResultGenerator.error;
import static com.yuchu.projectmfw.common.ResultGenerator.ok;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 10:02
 */
@RestController
@RequestMapping("/order")
@Api("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @ApiOperation(value = "保存订单")
    @PostMapping("/")
    public Result<String> save(@RequestBody Order order){
       try{
           order = orderService.saveOrder(order);
//           orderService.push2Mq(order.getId());
           return ok(order.getId());
       }catch (Exception e){
           e.printStackTrace();
           return error(e.getMessage());
       }
    }

    @ApiOperation(value = "获取订单")
    @GetMapping("/")
    public Result<Order> get(@RequestParam String id){
        try{
            Order order = orderService.getOrder(id);
            return ok(order);
        }catch (Exception e){
            e.printStackTrace();
            return error(e.getMessage());
        }
    }
}
