package com.example.order_management.service;

import com.example.order_management.dto.ProductDto;
import com.example.order_management.dto.SaveProductDto;
import com.example.order_management.enums.OrderStatus;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    List<ProductDto> getUserProductsWithStatus(Long userId, OrderStatus status);
}
