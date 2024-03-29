package com.masai.ecommerce.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.ecommerce.entity.OrderInfo;
import com.masai.ecommerce.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderInfo> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public OrderInfo saveOrder(OrderInfo order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
