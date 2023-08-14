package com.example.order_management.mapper;

import com.example.order_management.dto.OrderDto;
import com.example.order_management.entity.Order;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper extends AbstractConverter<Order, OrderDto> {
    @Override
    protected OrderDto convert(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userName(order.getUser().getName())
                .productName(order.getProduct().getName())
                .productImageUrl(order.getProduct().getImageUrl())
                .build();
    }
}
