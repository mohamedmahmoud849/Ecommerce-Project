package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.BaseOrderService;
import com.vodafone.ecommerce.service.ConfirmedOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ConfirmedOrderService orderService;
    @RequestMapping("/customers/{id}/orders")
    public String getCustomerOrders(@PathVariable("id") Long id,Model model){
        model.addAttribute("orders",orderService.getAllOrdersByCustomerId(id));
        return "customer_order";
    }

    @RequestMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model){
        model.addAttribute("order",orderService.getOrderDetails(id));
        model.addAttribute("order_items",orderService.getCardItemsForOrderDetails(orderService.getProjection(id)));
        return "order_details";
    }

}
