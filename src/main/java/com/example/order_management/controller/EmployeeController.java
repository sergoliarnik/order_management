package com.example.order_management.controller;

import com.example.order_management.dto.OrderDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final OrderService orderService;

    @GetMapping("/employee")
    public String employee() {
        return "employee";
    }

    @GetMapping("/employee/orders")
    @ResponseBody
    public List<OrderDto> getOrdersWithStatus(@RequestParam OrderStatus status){
        return orderService.getOrdersWithStatus(status);
    }
}
