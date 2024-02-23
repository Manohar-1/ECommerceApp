package com.masai.ecommerce.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.masai.ecommerce.entity.OrderInfo;
import com.masai.ecommerce.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<OrderInfo> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping
    public OrderInfo placeOrder(@RequestBody OrderInfo order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public void cancelOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
