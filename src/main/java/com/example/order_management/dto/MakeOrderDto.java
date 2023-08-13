package com.example.order_management.dto;

import lombok.Data;

@Data
public class MakeOrderDto {
    private long id;
    private String image;
    private String name;
    private long price;
    private long quantity;
}
