package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }
}
