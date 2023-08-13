package com.example.order_management.service;

import com.example.order_management.dto.MakeOrderDto;

import java.util.List;

public interface OrderService {
    void makeOrder(Long userId, List<MakeOrderDto> orders);
}
