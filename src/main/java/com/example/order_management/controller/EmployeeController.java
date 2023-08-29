package com.example.order_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    /**
     * Returns the JSP view for the employee page.
     *
     * @return The JSP view.
     */
    @GetMapping("/employee")
    public String employee() {
        return "employee";
    }
}
