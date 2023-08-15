package com.example.order_management.controller;

import com.example.order_management.dto.ProductDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    @ResponseBody
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }


    @GetMapping("/products_in_order")
    @ResponseBody
    public List<ProductDto> getUserProductsWithStatus(@RequestParam OrderStatus status){
        Long userId = 1L;
        return productService.getUserProductsWithStatus(userId, status);
    }
}
