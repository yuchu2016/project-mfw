package com.yuchu.projectmfw.repository;

import com.yuchu.projectmfw.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger,Long>{
    List<Passenger> findByOrderId(String orderId);
}
