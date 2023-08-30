package com.example.order_management.controller;

import com.example.order_management.ModelUtils;
import com.example.order_management.dto.ProductDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    @Test
    @SneakyThrows
    void getAllProductsReturns200() {
        List<ProductDto> expected = Collections.singletonList(ModelUtils.getProductDto());

        when(productService.getAllProducts()).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/products")
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualValueAsString = mvcResult.getResponse().getContentAsString();
        List<ProductDto> actual = objectMapper.readValue(actualValueAsString, new TypeReference<List<ProductDto>>() {
        });

        assertEquals(expected, actual);

        verify(productService).getAllProducts();
    }

    @Test
    @SneakyThrows
    void getAllProductsReturns200WhenEmptyResult() {
        List<ProductDto> expected = Collections.emptyList();

        when(productService.getAllProducts()).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/products")
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualValueAsString = mvcResult.getResponse().getContentAsString();
        List<ProductDto> actual = objectMapper.readValue(actualValueAsString, new TypeReference<List<ProductDto>>() {
        });

        assertEquals(expected, actual);

        verify(productService).getAllProducts();
    }

    @Test
    @SneakyThrows
    void getUserProductsWithStatusReturns200() {
        OrderStatus status = ModelUtils.ORDER_STATUS;
        List<ProductDto> expected = Collections.singletonList(ModelUtils.getProductDto());

        when(productService.getUserProductsWithStatus(1L, status)).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/products_in_order")
                        .param("status", status.toString())
                        .contentType("application/json")
                ).andExpect(status().isOk())
                .andReturn();
        String actualValueAsString = mvcResult.getResponse().getContentAsString();
        List<ProductDto> actual = objectMapper.readValue(actualValueAsString, new TypeReference<List<ProductDto>>() {
        });

        assertEquals(expected, actual);

        verify(productService).getUserProductsWithStatus(1L, status);
    }

    @Test
    @SneakyThrows
    void getUserProductsWithStatusReturns200WithEmptyResult() {
        OrderStatus status = ModelUtils.ORDER_STATUS;
        List<ProductDto> expected = Collections.emptyList();

        when(productService.getUserProductsWithStatus(1L, status)).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/products_in_order")
                        .param("status", status.toString())
                        .contentType("application/json")
                ).andExpect(status().isOk())
                .andReturn();
        String actualValueAsString = mvcResult.getResponse().getContentAsString();
        List<ProductDto> actual = objectMapper.readValue(actualValueAsString, new TypeReference<List<ProductDto>>() {
        });

        assertEquals(expected, actual);

        verify(productService).getUserProductsWithStatus(1L, status);
    }

    @Test
    @SneakyThrows
    void getUserProductsWithStatusReturns400WhenInvalidStatus() {
        mockMvc.perform(get("/products_in_order")
                        .param("status", "INVALID_STATUS")
                        .contentType("application/json")
                ).andExpect(status().isBadRequest())
                .andReturn();

        verify(productService, never()).getUserProductsWithStatus(anyLong(), any());
    }
}