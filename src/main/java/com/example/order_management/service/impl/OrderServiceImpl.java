package com.example.order_management.service.impl;

import com.example.order_management.dto.OrderedProductDto;
import com.example.order_management.dto.OrderDto;
import com.example.order_management.entity.Order;
import com.example.order_management.entity.Product;
import com.example.order_management.entity.User;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.exception.ResourceNotFoundException;
import com.example.order_management.repository.OrderRepo;
import com.example.order_management.repository.ProductRepo;
import com.example.order_management.repository.UserRepo;
import com.example.order_management.service.OrderService;
import com.example.order_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;
    private final ModelMapper mapper;
    private final SimpMessagingTemplate template;

    /**
     * @inheritDoc
     */
    @Override
    public void createOrders(long userId, List<OrderedProductDto> orderedProducts) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(userId)));

        List<Long> productIds = orderedProducts.stream()
                .map(OrderedProductDto::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepo.findAllById(productIds);

        List<Order> userOrders = new ArrayList<>();

        orderedProducts.forEach(order -> {
            Product product = products.stream().filter(prod -> prod.getId() == order.getProductId()).findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(order.getProductId())));
            for (int i = 0; i < order.getQuantity(); i++) {
                userOrders.add(Order.builder()
                        .user(user)
                        .product(product)
                        .status(OrderStatus.ORDERED)
                        .build());
            }
        });
        orderRepo.saveAll(userOrders);
        template.convertAndSend("/topic/orders", getOrdersWithStatus(OrderStatus.ORDERED));
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersWithStatus(OrderStatus status) {
        return orderRepo.findAllByStatus(status).stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order", "id", orderId.toString()));
        order.setStatus(status);
        template.convertAndSend("/topic/orders", getOrdersWithStatus(OrderStatus.ORDERED));
        template.convertAndSend("/topic/inprogress", getOrdersWithStatus(OrderStatus.INPROGRESS));
        template.convertAndSend("/topic/ready", getOrdersWithStatus(OrderStatus.READY));
        template.convertAndSend("/topic/current_user_inprogress", productService.getUserProductsWithStatus(order.getUser().getId(), OrderStatus.INPROGRESS));
        template.convertAndSend("/topic/current_user_ready", productService.getUserProductsWithStatus(order.getUser().getId(), OrderStatus.READY));
    }
}
