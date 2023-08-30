package com.example.order_management.repository;

import com.example.order_management.ModelUtils;
import com.example.order_management.entity.Order;
import com.example.order_management.entity.Product;
import com.example.order_management.entity.User;
import com.example.order_management.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SqlGroup({
        @Sql("sql/createUsers.sql"),
        @Sql("sql/createProducts.sql"),
        @Sql("sql/createOrders.sql")
})
class OrderRepoTest {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;

    @Test
    void findAllByStatus() {
        List<Order> result = orderRepo.findAllByStatus(OrderStatus.ORDERED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getId());
    }

    @Test
    void findAllByStatusWithOrderedStatus() {
        List<Order> result = orderRepo.findAllByStatus(OrderStatus.ORDERED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getId());
    }

    @Test
    void findAllByStatusWithInprogressStatus() {
        List<Order> result = orderRepo.findAllByStatus(OrderStatus.INPROGRESS);

        assertNotNull(result);
        assertEquals(3, result.size());

        List<Long> resultIds = result.stream().map(Order::getId).collect(Collectors.toList());
        assertTrue(resultIds.contains(1L));
        assertTrue(resultIds.contains(4L));
        assertTrue(resultIds.contains(6L));
    }

    @Test
    void findAllByStatusWithReadyStatus() {
        List<Order> result = orderRepo.findAllByStatus(OrderStatus.READY);

        assertNotNull(result);
        assertEquals(3, result.size());

        List<Long> resultIds = result.stream().map(Order::getId).collect(Collectors.toList());
        assertTrue(resultIds.contains(2L));
        assertTrue(resultIds.contains(5L));
        assertTrue(resultIds.contains(7L));
    }

    @Test
    void findAllByStatusWithDeclinedStatus() {
        List<Order> result = orderRepo.findAllByStatus(OrderStatus.DECLINED);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}