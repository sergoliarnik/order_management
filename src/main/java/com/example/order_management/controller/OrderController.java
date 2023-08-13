package com.example.order_management.controller;

import com.example.order_management.dto.MakeOrderDto;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/makeOrder")
    public void makeOrder(@RequestBody List<MakeOrderDto> orders){
        Long userId = 1L;
        orderService.makeOrder(userId, orders);
    }
}
