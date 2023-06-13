package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminController {

    AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @GetMapping("/edit_admins")
    public String editAdminsPage(){
        return "edit_admins";
    }

    @GetMapping("/edit_inventory")
    public String editInventoryPage(){
        return "edit_inventory";
    }
}

