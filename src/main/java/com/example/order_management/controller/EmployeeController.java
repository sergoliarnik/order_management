package com.example.order_management.controller;

import com.example.order_management.dto.OrderDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final OrderService orderService;
    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("orderedList", orderService.getUserOrderWithStatus(OrderStatus.ORDERED));
        model.addAttribute("inprogressList", orderService.getUserOrderWithStatus(OrderStatus.INPROGRESS));
        model.addAttribute("readyList", orderService.getUserOrderWithStatus(OrderStatus.READY));
        return "employee";
    }
}
