package com.example.order_management.controller;

import com.example.order_management.dto.MakeOrderDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public void saveOrders(@RequestBody List<MakeOrderDto> orders){
        Long userId = 1L;
        orderService.saveOrders(userId, orders);
    }
    @PatchMapping("/orders/{id}")
    @ResponseBody
    public void updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status){
        orderService.updateOrderStatus(id, status);
    }
}
