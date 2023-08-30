package com.example.order_management.repository;

import com.example.order_management.entity.Order;
import com.example.order_management.entity.Product;
import com.example.order_management.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SqlGroup({
        @Sql("sql/createUsers.sql"),
        @Sql("sql/createProducts.sql"),
        @Sql("sql/createOrders.sql")
})
class ProductRepoTest {
    @Autowired
    ProductRepo productRepo;

    @Test
    void getUserProductsWithStatusOrdered() {
        List<Product> productList = productRepo.getUserProductsWithStatus(1L, OrderStatus.ORDERED);

        assertNotNull(productList);
        assertEquals(1, productList.size());
        assertEquals(2L, productList.get(0).getId());
    }

    @Test
    void getUserProductsWithStatusInprogress() {
        List<Product> productList = productRepo.getUserProductsWithStatus(2L, OrderStatus.INPROGRESS);

        assertNotNull(productList);
        assertEquals(2, productList.size());

        List<Long> resultIds = productList.stream().map(Product::getId).collect(Collectors.toList());
        assertTrue(resultIds.contains(2L));
        assertTrue(resultIds.contains(3L));
    }

    @Test
    void getUserProductsWithStatusWhenEmptyResult() {
        List<Product> productList = productRepo.getUserProductsWithStatus(2L, OrderStatus.ORDERED);

        assertNotNull(productList);
        assertTrue(productList.isEmpty());
    }
}