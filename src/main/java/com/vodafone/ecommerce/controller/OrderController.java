package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.UserRepository;

import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImbl.ConfirmedOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class OrderController {

    private final UserService userService;
    private final ConfirmedOrderService orderService;
    @RequestMapping("/orders")
    public String getCustomerOrders(Model model){
        Long loggedInUserId = userService.getCurrentLoggedInUser().getId();
        List<Order> loggedInUserOrders = orderService.getAllOrdersByCustomerId(loggedInUserId);
        if (loggedInUserOrders.isEmpty()){
            return "empty_orders_message";
        }
        model.addAttribute("orders",loggedInUserOrders);
        return "new_customer_order_page";
    }

    @RequestMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model){
        model.addAttribute("order",orderService.getOrderDetails(id));
        model.addAttribute("order_items",orderService.getCardItemsForOrderDetails(orderService.getProjection(id)));
        return "new_order_details_page";
    }


}
