package com.example.order_management.controller;

import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final OrderService orderService;
    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("orderedList", orderService.getUserOrdersWithStatus(OrderStatus.ORDERED));
        model.addAttribute("inprogressList", orderService.getUserOrdersWithStatus(OrderStatus.INPROGRESS));
        model.addAttribute("readyList", orderService.getUserOrdersWithStatus(OrderStatus.READY));
        return "employee";
    }
}
