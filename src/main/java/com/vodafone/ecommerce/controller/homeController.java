package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class homeController {

    private final productService productService;
    private final CartService cartService;
    private final HttpSession httpSession;
    //TODO: change this list into session list when user log in successfully
    private List<Product> productsList = new ArrayList<>();

    @GetMapping("/")
    public ModelAndView homePage(Model model){
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        httpSession.setAttribute("cart_items_list",productsList);
        List<Product> currentCart =  (List<Product>) httpSession.getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        log.info(String.valueOf(currentCart.size()));
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        return new ModelAndView("home","products",productService.getALlByCategory("Pizza"));
    }
    @GetMapping("/salad")
    public ModelAndView saladPage(Model model){
        List<Product> currentCart =  (List<Product>) httpSession.getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        return new ModelAndView("home","products",productService.getALlByCategory("Salad"));
    }
    @GetMapping("/noodle")
    public ModelAndView noodlePage(Model model){
        List<Product> currentCart =  (List<Product>) httpSession.getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        return new ModelAndView("home","products",productService.getALlByCategory("Noodle"));
    }
    @RequestMapping("/{id}")
    public String addNewItemToCart(@PathVariable Integer id, @RequestParam("quantity") Integer quantity, Model model){
        List<Product> currentCart =  (List<Product>) httpSession.getAttribute("cart_items_list");
        List<Product> updatedCart = cartService.addNewCartItem(currentCart,id,quantity);
        httpSession.setAttribute("cart_items_list",updatedCart);
        httpSession.setAttribute("total_price",productService.calculateTotalPrice(updatedCart));
        model.addAttribute("list",updatedCart);
        return "redirect:/";
    }
}
