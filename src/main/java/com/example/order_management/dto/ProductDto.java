package com.example.order_management.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String imageUrl;
    private long price;
}
