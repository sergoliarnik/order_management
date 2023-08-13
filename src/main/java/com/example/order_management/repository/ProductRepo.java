package com.example.order_management.repository;

import com.example.order_management.entity.Product;
import com.example.order_management.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("FROM Product p JOIN p.orders o WHERE o.user.id = :userId AND o.status = :orderStatus")
    List<Product> getUserProductsWithStatus(Long userId, OrderStatus orderStatus);
}
