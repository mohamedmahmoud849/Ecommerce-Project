package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.errorhandlling.EmailAlreadyExistsException;
import com.vodafone.ecommerce.errorhandlling.UsernameAlreadyExistsException;
import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImbl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

    AdminService adminService;
    private UserService userService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_admin")
    public String showAddAminForm(){
        return "new_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_admin")
    public String addNewAdmin(@RequestParam("email") String email,
                              @RequestParam("username") String username,
                              @RequestParam("password") CharSequence password) {
        try {
            adminService.addAdmin(email, username, password);
        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e) {
            return "add_admin?error=" + e.getMessage();
        }

        return "redirect:/edit_admins";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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
    @GetMapping("/admin_home")
    public String adminHomePage(){
        return "admin_home";
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update_admin/{id}")
    public String updateAdminDetails(@PathVariable Long id, Model model) {
        model.addAttribute("user", adminService.findById(id));
        return "update_admin";}

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update_admin/{id}")
    public String updateAdminDetails(@PathVariable Long id,
                                     @RequestParam("username") String username,
                                     @RequestParam("email") String email,
                                     @RequestParam("state") State state) {
        try {
            adminService.updateAdmin(id, username, email, state);
        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e) {
            return "add_admin?error=" + e.getMessage();
        }

        return "redirect:/update_admin_list";
        }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete_admin/{id}")
    public String deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return "redirect:/update_admin_list";}

}

