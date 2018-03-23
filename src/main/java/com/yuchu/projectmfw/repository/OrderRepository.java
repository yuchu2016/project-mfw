package com.yuchu.projectmfw.repository;

import com.yuchu.projectmfw.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 10:00
 */

public interface OrderRepository extends JpaRepository<Order,String>{

}
