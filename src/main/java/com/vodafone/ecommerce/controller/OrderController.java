package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.UserRepository;
import com.vodafone.ecommerce.service.BaseOrderService;
import com.vodafone.ecommerce.serviceImbl.ConfirmedOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.security.Principal;
import java.util.List;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    UserRepository userRepository;

    private final ConfirmedOrderService orderService;
    @RequestMapping("/customers/{id}/orders")
    public String getCustomerOrders(@PathVariable("id") Long id, Model model, Principal principal){

        String email = principal.getName();
        UserEntity user = userRepository.findByEmail(email);
        Long userId = user.getId();
            model.addAttribute("orders",orderService.getAllOrdersByCustomerId(userId));
            return "customer_order";
    }

    @RequestMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model){
        model.addAttribute("order",orderService.getOrderDetails(id));
        model.addAttribute("order_items",orderService.getCardItemsForOrderDetails(orderService.getProjection(id)));
        return "order_details";
    }

    @RequestMapping("/payByCard")
    public String showPayByCardPage(Model model){
        return "pay-by-card";
    }
}
