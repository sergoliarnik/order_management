package com.example.order_management.service;

import com.example.order_management.dto.OrderedProductDto;
import com.example.order_management.dto.OrderDto;
import com.example.order_management.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    /**
     * Creates a list of orders for the specified user.
     *
     * @param userId The ID of the user.
     * @param orderedProducts The products to order.
     */
    void createOrders(long userId, List<OrderedProductDto> orderedProducts);

    /**
     * Returns a list of orders with the specified status.
     *
     * @param status The status of the orders.
     * @return A list of orders.
     */
    List<OrderDto> getOrdersWithStatus(OrderStatus status);

    /**
     * Updates the status of the order with the specified ID.
     *
     * @param id The ID of the order.
     * @param status The new status of the order.
     */
    void updateOrderStatus(Long id, OrderStatus status);
}
