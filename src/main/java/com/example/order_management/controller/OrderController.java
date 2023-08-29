package com.example.order_management.controller;

import com.example.order_management.dto.OrderedProductDto;
import com.example.order_management.dto.OrderDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    /**
     * Saves a list of orders for the specified user.
     *
     * @param userId The ID of the user.
     * @param orderedProducts The product to order.
     */
    @PostMapping("/orders")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveOrders(@RequestBody @Valid List<OrderedProductDto> orderedProducts){
        long userId = 1L;
        orderService.createOrders(userId, orderedProducts);
    }

    /**
     * Updates the status of the order with the specified ID.
     *
     * @param id The ID of the order.
     * @param status The new status of the order.
     */
    @PatchMapping("/orders/{id}")
    @ResponseBody
    public void updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status){
        orderService.updateOrderStatus(id, status);
    }

    /**
     * Gets a list of orders with the specified status.
     *
     * @param status The status of the orders to get.
     * @return A list of orders.
     */
    @GetMapping("/employee/orders")
    @ResponseBody
    public List<OrderDto> getOrdersWithStatus(@RequestParam OrderStatus status){
        return orderService.getOrdersWithStatus(status);
    }
}
