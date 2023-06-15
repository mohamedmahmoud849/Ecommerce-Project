package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImbl.PaymentService;
import com.vodafone.ecommerce.serviceImbl.ProductService;
import com.vodafone.ecommerce.serviceImbl.UnConfirmedOrderService;
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
public class CartController extends BaseController{

    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestService restService;
    private final UnConfirmedOrderService orderService;
    private final UserService userService;




    @RequestMapping("/cart")
    public String showCartPage(Model model) {
        List<Product> unconfirmedOrderProducts = orderService.getCurentUserUnconfirmedOrderProductsList();
        if (unconfirmedOrderProducts.isEmpty()){
            model.addAttribute("customer_id", orderService.getCurrentUserId());
            return "empty_cart_message";
        }
        String unconfirmedOrderTotalPrice = orderService.calculateOrderTotalPrice(unconfirmedOrderProducts);
        model.addAttribute("customer_id", orderService.getCurrentUserId());
        model.addAttribute("items", unconfirmedOrderProducts);
        model.addAttribute("total_price", unconfirmedOrderTotalPrice);
        return "cart_page";
    }
    @RequestMapping("/{id}")
    public String addNewItemToCart(@PathVariable Long id, @RequestParam("quantity") Integer quantity){
        orderService.addNewCartItem(id,quantity);
        return "redirect:/";
    }

    @RequestMapping("/delete_cart_item/{name}")
    public String deleteCardItem(@PathVariable String name){
        orderService.deleteItemFromUnconfirmedOrderById(name);
        return "redirect:/cart";
    }

    ///TODO: link logged in customer with order
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model){
        model.addAttribute("order_items",orderService.getCurentUserUnconfirmedOrderProductsList());
        model.addAttribute("order",orderService.getCurentUserUnconfirmedOrder());
        return "pre_pay_details";
    }
}

