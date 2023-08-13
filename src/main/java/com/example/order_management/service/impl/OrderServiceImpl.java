package com.example.order_management.service.impl;

import com.example.order_management.dto.MakeOrderDto;
import com.example.order_management.entity.Order;
import com.example.order_management.entity.Product;
import com.example.order_management.entity.User;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.repository.OrderRepo;
import com.example.order_management.repository.ProductRepo;
import com.example.order_management.repository.UserRepo;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    @Override
    public void makeOrder(Long userId, List<MakeOrderDto> orders) {
        List<Order> userOrders = new ArrayList<>();
        User user = userRepo.findById(userId).get();
        orders.forEach(order -> {
            Product product = productRepo.findById(order.getId()).get();
            for(int i = 0; i< order.getQuantity();i++){
                userOrders.add(Order.builder()
                        .user(user)
                        .product(product)
                        .status(OrderStatus.INPROGRESS)
                        .build());
            }
        });
        orderRepo.saveAll(userOrders);
    }
}
