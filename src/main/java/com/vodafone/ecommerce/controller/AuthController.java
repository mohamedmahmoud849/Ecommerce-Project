package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result, Model model) throws MessagingException {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);

        //TODO: show a message telling user to verify via email upon registration
        return "redirect:/register/verify";
    }

    @GetMapping("/register/verify")
    public String showVerifyByEmailMessage(Model model){
        return "verify_by_email_message_page";
    }
    @GetMapping("/verify/{id}")
    public String verifyEmail(@PathVariable("id") Long id){
        userService.verifyState(id);
        return "redirect:/";
    }
    @GetMapping("/reset")
    public String showResetPasswordMessage(Model model){
        model.addAttribute("message","Please Check Your Email TO Login");
        return "login";
    }
    @GetMapping("/reset/{id}")
    public String showResetPasswordForm(@PathVariable("id") Long id,Model model){
        model.addAttribute("id",id);
        return "password_reset";
    }
    @PostMapping("/reset/{id}")
    public String submitResetPasswordForm(@PathVariable("id") Long id,@RequestParam("newPassword") String newPassword){
        userService.resetPassword(newPassword,id);
        return "redirect:/login";
    }



}