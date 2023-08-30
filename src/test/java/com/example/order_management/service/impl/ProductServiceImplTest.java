package com.example.order_management.service.impl;

import com.example.order_management.ModelUtils;
import com.example.order_management.dto.ProductDto;
import com.example.order_management.entity.Product;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.repository.ProductRepo;
import com.example.order_management.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepo productRepo;
    @Mock
    ModelMapper modelMapper;

    @Test
    void testGetAllProducts() {
        Product product = ModelUtils.getProduct();
        ProductDto productDto = ModelUtils.getProductDto();

        when(productRepo.findAll()).thenReturn(Collections.singletonList(product));
        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        List<ProductDto> result = productService.getAllProducts();

        assertEquals(Collections.singletonList(productDto), result);

        verify(productRepo).findAll();
        verify(modelMapper).map(product, ProductDto.class);
    }

    @Test
    void testGetAllProductWhenEmpty() {
        when(productRepo.findAll()).thenReturn(Collections.emptyList());

        List<ProductDto> result = productService.getAllProducts();

        assertEquals(Collections.emptyList(), result);

        verify(productRepo).findAll();
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void getUserProductsWithStatus() {
        long userId = ModelUtils.USER_ID;
        OrderStatus status = OrderStatus.ORDERED;
        Product product = ModelUtils.getProduct();
        ProductDto productDto = ModelUtils.getProductDto();

        when(productRepo.getUserProductsWithStatus(userId, status)).thenReturn(Collections.singletonList(product));
        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        List<ProductDto> result = productService.getUserProductsWithStatus(userId, status);

        assertEquals(Collections.singletonList(productDto), result);

        verify(productRepo).getUserProductsWithStatus(userId, status);
        verify(modelMapper).map(product, ProductDto.class);
    }

    @Test
    void getUserProductsWithStatusWhenEmpty() {
        long userId = ModelUtils.USER_ID;
        OrderStatus status = OrderStatus.ORDERED;

        when(productRepo.getUserProductsWithStatus(userId, status)).thenReturn(Collections.emptyList());

        List<ProductDto> result = productService.getUserProductsWithStatus(userId, status);

        assertEquals(Collections.emptyList(), result);

        verify(productRepo).getUserProductsWithStatus(userId, status);
        verify(modelMapper, never()).map(any(), any());
    }
}