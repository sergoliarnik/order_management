package com.example.order_management.controller;

import com.example.order_management.dto.ProductDto;
import com.example.order_management.dto.SaveProductDto;
import com.example.order_management.enums.OrderStatus;
import com.example.order_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }


    @GetMapping("/products_in_order/{status}")
    @ResponseBody
    public List<ProductDto> getUserProductsWithStatus(@PathVariable OrderStatus status){
        Long userId = 1L;
        return productService.getUserProductsWithStatus(userId, status);
    }
}
