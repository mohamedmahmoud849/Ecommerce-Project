package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

    AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/add_admin")
    public String showAddAminForm(){
        return "new_admin";
    }
    @PostMapping("/add_admin")
    public String addNewAdmin(@RequestParam("email") String email,
                              @RequestParam("username") String username,
                              @RequestParam("password") CharSequence password) {
        adminService.addAdmin(email, username, password);
        return "redirect:/edit_admins";
    }

    @GetMapping("/update_admin_list")
    public String showEditAdminList(Model model){
        model.addAttribute("users", adminService.getALlAdmins());
        return "admin_edit_list";
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

    @GetMapping("/update_admin/{id}")
    public String updateAdminDetails(@PathVariable Long id, Model model) {
        model.addAttribute("user", adminService.findById(id));
        return "update_admin";}

    @PostMapping("/update_admin/{id}")
    public String updateAdminDetails(@PathVariable Long id,
                                     @RequestParam("username") String username,
                                     @RequestParam("email") String email,
                                     @RequestParam("state") State state) {
        adminService.updateAdmin(id, username, email, state);
        return "redirect:/update_admin_list";}

    @GetMapping("/delete_admin/{id}")
    public String deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return "redirect:/update_admin_list";}

    //@PutMapping()
}

