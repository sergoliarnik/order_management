package com.example.order_management.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class OrderedProductDto {
    @JsonAlias("id")
    private long productId;
    @Min(1)
    @Max(100)
    private long quantity;
}
