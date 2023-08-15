package com.example.order_management.service;

import com.example.order_management.dto.MakeOrderDto;
import com.example.order_management.dto.OrderDto;
import com.example.order_management.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    void saveOrders(Long userId, List<MakeOrderDto> orders);

    List<OrderDto> getOrdersWithStatus(OrderStatus status);

    void updateOrderStatus(Long id, OrderStatus status);
}
