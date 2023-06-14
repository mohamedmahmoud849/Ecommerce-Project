package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.AdminService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminController {

    AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit_admins")
    public String editAdminsPage(){
        return "edit_admins";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit_inventory")
    public String editInventoryPage(){
        return "edit_inventory";
    }
}

