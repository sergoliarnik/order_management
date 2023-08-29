package com.example.order_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    /**
     * Returns the JSP view for the user page.
     *
     * @return The JSP view.
     */
    @GetMapping("/user")
    public String user(){
        return "user";
    }
}
