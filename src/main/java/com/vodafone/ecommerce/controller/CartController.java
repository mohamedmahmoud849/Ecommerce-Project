package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImbl.PaymentService;
import com.vodafone.ecommerce.serviceImbl.ProductService;
import com.vodafone.ecommerce.serviceImbl.UnConfirmedOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cart")
public class CartController extends BaseController{

    private final UnConfirmedOrderServiceImpl orderService;


    @GetMapping
    public String showCartPage(@RequestParam(name = "message", required = false) String message,Model model) {
        List<Product> unconfirmedOrderProducts = orderService.getCurentUserUnconfirmedOrderProductsList();
        if (unconfirmedOrderProducts.isEmpty()){
            return "empty_cart_message";
        }
        String unconfirmedOrderTotalPrice = orderService.calculateOrderTotalPrice(unconfirmedOrderProducts);
        model.addAttribute("message",message);
        model.addAttribute("customer_id", orderService.getCurrentUserId());
        model.addAttribute("items", unconfirmedOrderProducts);
        model.addAttribute("total_price", unconfirmedOrderTotalPrice);
        return "new_cart_page";
    }
    @RequestMapping("/{id}")
    public String addNewItemToCart(@PathVariable Long id, @RequestParam("quantity") Integer quantity){
        orderService.addNewCartItem(id,quantity);
        return "redirect:/";
    }

    @RequestMapping("/delete_item/{name}")
    public String deleteCardItem(@PathVariable String name){
        orderService.deleteItemFromUnconfirmedOrderById(name);
        return "redirect:/cart";
    }

    ///TODO: link logged in customer with order
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model){
        model.addAttribute("order_items",orderService.getCurentUserUnconfirmedOrderProductsList());
        model.addAttribute("order",orderService.getCurentUserUnconfirmedOrder());
        return "new_pre_pay_page";
    }

    @PostMapping("/update/{id}")
    public String updateCartItemQuantity(@PathVariable("id") Long item_id ,
                                         @RequestParam("quantity") Integer item_quantity){
        orderService.updateUnconfirmedOrderItemQuantity(item_id,item_quantity);
        return "redirect:/cart";
    }
}

