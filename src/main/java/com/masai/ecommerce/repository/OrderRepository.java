package com.masai.ecommerce.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.ecommerce.entity.OrderInfo;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {
    
    List<OrderInfo> findByUserId(Long userId);
}
