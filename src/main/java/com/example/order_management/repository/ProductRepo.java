package com.example.order_management.repository;

import com.example.order_management.entity.Product;
import com.example.order_management.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    /**
     * Returns a list of products that the user has ordered with the specified status.
     *
     * @param userId The ID of the user.
     * @param orderStatus The status of the orders.
     * @return A list of products.
     */
    @Query("FROM Product p JOIN p.orders o WHERE o.user.id = :userId AND o.status = :orderStatus")
    List<Product> getUserProductsWithStatus(Long userId, OrderStatus orderStatus);
}
