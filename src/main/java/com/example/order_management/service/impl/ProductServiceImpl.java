package com.example.order_management.service.impl;

import com.example.order_management.dto.ProductDto;
import com.example.order_management.dto.SaveProductDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.repository.ProductRepo;
import com.example.order_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper mapper;
    private final ProductRepo productRepo;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepo.findAll().stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getUserProductsWithStatus(Long userId, OrderStatus status) {
        return productRepo.getUserProductsWithStatus(userId, status).stream()
                .map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

    }
}
