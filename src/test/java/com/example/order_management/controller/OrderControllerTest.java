package com.example.order_management.controller;

import com.example.order_management.ModelUtils;
import com.example.order_management.dto.OrderDto;
import com.example.order_management.dto.OrderedProductDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @SneakyThrows
    void createOrdersReturns201() {
        List<OrderedProductDto> orderedProductDtos = Collections.singletonList(ModelUtils.getOrderedProductDto());
        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderedProductDtos)))
                .andExpect(status().isCreated());

        verify(orderService).createOrders(1L, orderedProductDtos);
    }

    @Test
    @SneakyThrows
    void createOrdersReturns400WhenInvalidOrderedProductDtos() {
        OrderedProductDto orderedProductDto = ModelUtils.getOrderedProductDto();
        orderedProductDto.setQuantity(101);
        List<OrderedProductDto> orderedProductDtos = Collections.singletonList(orderedProductDto);
        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderedProductDtos)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void updateOrderStatusReturns200() {
        Long orderId = ModelUtils.ORDER_ID;
        OrderStatus status = ModelUtils.ORDER_STATUS;

        mockMvc.perform((patch("/orders/{id}", orderId))
                .param("status", status.toString())
        ).andExpect(status().isOk());

        verify(orderService).updateOrderStatus(orderId, status);
    }

    @Test
    @SneakyThrows
    void updateOrderStatusReturns400WhenInvalidStatus() {
        Long orderId = ModelUtils.ORDER_ID;

        mockMvc.perform((patch("/orders/{id}", orderId))
                .param("status", "INVALID_STATUS")
        ).andExpect(status().isBadRequest());

        verify(orderService, never()).updateOrderStatus(anyLong(), any());
    }

    @Test
    @SneakyThrows
    void updateOrderStatusReturns400WhenInvalidOrderId() {
        OrderStatus status = ModelUtils.ORDER_STATUS;

        mockMvc.perform((patch("/orders/{id}", "INVALID_ORDER_ID"))
                .param("status", status.toString())
        ).andExpect(status().isBadRequest());

        verify(orderService, never()).updateOrderStatus(anyLong(), any());
    }

    @Test
    @SneakyThrows
    void getOrdersWithStatusReturns200() {
        OrderStatus status = ModelUtils.ORDER_STATUS;
        List<OrderDto> expected = Collections.singletonList(ModelUtils.getOrderDto());

        when(orderService.getOrdersWithStatus(status)).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/employee/orders")
                        .param("status", status.toString())
                ).andExpect(status().isOk())
                .andReturn();

        String actualValueAsString = mvcResult.getResponse().getContentAsString();
        List<OrderDto> actual = objectMapper.readValue(actualValueAsString, new TypeReference<List<OrderDto>>(){});

        assertEquals(expected, actual);

        verify(orderService).getOrdersWithStatus(status);
    }

    @Test
    @SneakyThrows
    void getOrdersWithStatusReturns200WhenEmptyResult() {
        OrderStatus status = ModelUtils.ORDER_STATUS;
        List<OrderDto> expected = Collections.emptyList();

        when(orderService.getOrdersWithStatus(status)).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/employee/orders")
                        .param("status", status.toString())
                ).andExpect(status().isOk())
                .andReturn();

        String actualValueAsString = mvcResult.getResponse().getContentAsString();
        List<OrderDto> actual = objectMapper.readValue(actualValueAsString, new TypeReference<List<OrderDto>>(){});

        assertEquals(expected, actual);

        verify(orderService).getOrdersWithStatus(status);
    }

    @Test
    @SneakyThrows
    void getOrdersWithStatusReturns400WhenInvalidStatus() {
        mockMvc.perform(get("/employee/orders")
                        .param("status", "INVALID_STATUS")
                ).andExpect(status().isBadRequest())
                .andReturn();

        verify(orderService, never()).getOrdersWithStatus(any());
    }
}