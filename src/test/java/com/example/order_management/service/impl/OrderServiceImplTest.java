package com.example.order_management.service.impl;

import com.example.order_management.ModelUtils;
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
import com.example.order_management.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private ModelMapper mapper;
    @Mock
    private OrderRepo orderRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private SimpMessagingTemplate template;
    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void testSaveOrders() {
        OrderServiceImpl orderServiceSpy = spy(orderService);
        long userId = ModelUtils.USER_ID;
        long productId = ModelUtils.PRODUCT_ID;
        List<Long> productIds = Collections.singletonList(productId);
        OrderedProductDto orderedProductDto = ModelUtils.getOrderedProductDto();
        List<OrderedProductDto> orderedProducts = Collections.singletonList(orderedProductDto);
        User user = ModelUtils.getUser();
        Product product = ModelUtils.getProduct();
        Order order = ModelUtils.getOrder();
        order.setId(null);
        order.setUser(user);
        List<Order> userOrders = Collections.singletonList(order);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(productRepo.findAllById(productIds))
                .thenReturn(Collections.singletonList(product));

        orderServiceSpy.createOrders(userId, orderedProducts);

        verify(userRepo).findById(userId);
        verify(productRepo).findAllById(productIds);
        verify(orderRepo).saveAll(userOrders);
        verify(template)
                .convertAndSend("/topic/orders", orderServiceSpy.getOrdersWithStatus(OrderStatus.ORDERED));
    }

    @Test
    void testSaveOrdersWhenUserNotFound() {
        Long userId = ModelUtils.USER_ID;
        OrderServiceImpl orderServiceSpy = spy(orderService);
        OrderedProductDto orderedProductDto = ModelUtils.getOrderedProductDto();
        List<OrderedProductDto> orderedProducts = Collections.singletonList(orderedProductDto);

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> orderServiceSpy.createOrders(ModelUtils.USER_ID, orderedProducts));

        assertEquals(String.format("User not found with id : '%d'", userId), ex.getMessage());

        verify(userRepo).findById(userId);
        verify(productRepo, never()).findAllById(any());
        verify(orderRepo, never()).saveAll(any());
        verify(template, never())
                .convertAndSend(anyString(), any(Object.class));
    }

    @Test
    void testSaveOrdersWhenProductNotFound() {
        OrderServiceImpl orderServiceSpy = spy(orderService);
        long userId = ModelUtils.USER_ID;
        long productId = ModelUtils.PRODUCT_ID;
        OrderedProductDto orderedProductDto = ModelUtils.getOrderedProductDto();
        List<OrderedProductDto> orderedProducts = Collections.singletonList(orderedProductDto);
        User user = ModelUtils.getUser();

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(productRepo.findAllById(Collections.singletonList(productId)))
                .thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> orderServiceSpy.createOrders(userId, orderedProducts));

        assertEquals(String.format("Product not found with id : '%d'", productId), ex.getMessage());

        verify(userRepo).findById(userId);
        verify(productRepo).findAllById(Collections.singletonList(productId));
        verify(orderRepo, never()).saveAll(any());
        verify(template, never())
                .convertAndSend(anyString(), any(Object.class));
    }

    @Test
    void testGetOrdersWithStatus() {
        OrderStatus status = OrderStatus.ORDERED;
        Order order = ModelUtils.getOrder();
        OrderDto orderDto = ModelUtils.getOrderDto();

        when(orderRepo.findAllByStatus(status)).thenReturn(Collections.singletonList(order));
        when(mapper.map(order, OrderDto.class)).thenReturn(orderDto);

        List<OrderDto> list = orderService.getOrdersWithStatus(status);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(orderDto, list.get(0));

        verify(orderRepo).findAllByStatus(status);
        verify(mapper).map(order, OrderDto.class);
    }

    @Test
    void testUpdateOrderStatus() {
        long orderId = ModelUtils.ORDER_ID;
        long userId = ModelUtils.USER_ID;
        Order order = ModelUtils.getOrder();
        order.setUser(ModelUtils.getUser());
        OrderStatus newStatus = OrderStatus.READY;

        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepo.findAllByStatus(any())).thenReturn(Collections.emptyList());
        when(productService.getUserProductsWithStatus(eq(userId), any())).thenReturn(Collections.emptyList());
        orderService.updateOrderStatus(orderId, newStatus);

        assertEquals(newStatus, order.getStatus());

        verify(orderRepo).findById(orderId);
        verify(orderRepo, times(3)).findAllByStatus(any());
        verify(productService, times(2)).getUserProductsWithStatus(eq(userId), any());
        verify(orderRepo, never()).save(any());
    }
}