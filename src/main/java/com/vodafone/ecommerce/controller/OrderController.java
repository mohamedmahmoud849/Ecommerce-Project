package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @RequestMapping("/Customer_order")
    public String getCustomerOrders(Model model){
        model.addAttribute("orders",orderService.getAllOrders());
        return "customer_order";
    }

    @RequestMapping("/order/{id}")
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
