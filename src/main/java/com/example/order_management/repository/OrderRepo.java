package com.example.order_management.repository;

import com.example.order_management.entity.Order;
import com.example.order_management.enums.OrderStatus;
import liquibase.pro.packaged.Q;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    /**
     * Finds all orders with the specified status.
     *
     * @param status The status of the orders.
     * @return A list of orders.
     */
    @Query("FROM Order o JOIN FETCH o.user JOIN FETCH o.product WHERE o.status = :status")
    List<Order> findAllByStatus(OrderStatus status);
}
