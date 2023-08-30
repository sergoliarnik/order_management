package com.example.order_management;

import com.example.order_management.dto.OrderDto;
import com.example.order_management.dto.OrderedProductDto;
import com.example.order_management.dto.ProductDto;
import com.example.order_management.entity.Order;
import com.example.order_management.entity.Product;
import com.example.order_management.entity.User;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.enums.UserRole;

public final class ModelUtils {
    public static final long USER_ID = 1L;
    public static final long PRODUCT_ID = 2L;
    public static final long ORDER_ID = 3L;
    public static final String USER_NAME = "name";
    public static final String USER_PASSWORD = "password";
    public static final String PRODUCT_IMAGE_URL = "image_url";
    public static final String PRODUCT_NAME = "product_name";
    public static final long PRODUCT_PRICE = 20;

    public static final OrderStatus ORDER_STATUS = OrderStatus.ORDERED;

    public static User getUser(){
        return User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .role(UserRole.USER)
                .build();
    }

    public static Product getProduct(){
        return Product.builder()
                .imageUrl(PRODUCT_IMAGE_URL)
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .build();
    }

    public static Order getOrder(){
        return Order.builder()
                .id(ORDER_ID)
                .user(getUser())
                .product(getProduct())
                .status(OrderStatus.ORDERED)
                .build();
    }

    public static OrderedProductDto getOrderedProductDto(){
        return OrderedProductDto.builder()
                .productId(PRODUCT_ID)
                .quantity(1L)
                .build();
    }

    public static OrderDto getOrderDto(){
        return OrderDto.builder()
                .id(ORDER_ID)
                .userName(USER_NAME)
                .productName(PRODUCT_NAME)
                .productImageUrl(PRODUCT_IMAGE_URL)
                .build();
    }

    public static ProductDto getProductDto(){
        return ProductDto.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();
    }

    private ModelUtils(){

    }
}
