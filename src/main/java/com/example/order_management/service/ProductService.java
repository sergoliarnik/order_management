package com.example.order_management.service;

import com.example.order_management.dto.ProductDto;
import com.example.order_management.enums.OrderStatus;

import java.util.List;

public interface ProductService {
    /**
     * Returns a list of all products.
     *
     * @return A list of all products.
     */
    List<ProductDto> getAllProducts();

    /**
     * Returns a list of products that the user has ordered with the specified status.
     *
     * @param userId The ID of the user.
     * @param status The status of the orders.
     * @return A list of products.
     */
    List<ProductDto> getUserProductsWithStatus(Long userId, OrderStatus status);
}
