package com.example.order_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private long id;
    private String userName;
    private String productName;
    private String productImageUrl;
}
