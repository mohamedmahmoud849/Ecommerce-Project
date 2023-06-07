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
public class HomeController extends BaseController{

    private final productService productService;
    private final CartService cartService;

    //TODO: change this list into session list when user log in successfully



    @GetMapping("/")
    public ModelAndView homePage(Model model){
        if(getSession().getAttribute("cart_items_list") == null){
            getSession().setAttribute("cart_items_list",new ArrayList<>());
        }
        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        return new ModelAndView("home","products",productService.getALlByCategory("Pizza"));
    }
    @GetMapping("/salad")
    public ModelAndView saladPage(Model model){

        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        return new ModelAndView("home","products",productService.getALlByCategory("Salad"));
    }
    @GetMapping("/noodle")
    public ModelAndView noodlePage(Model model){

        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        return new ModelAndView("home","products",productService.getALlByCategory("Noodle"));
    }
    @RequestMapping("/{id}")
    public String addNewItemToCart(@PathVariable Long id, @RequestParam("quantity") Integer quantity, Model model){

        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        List<Product> updatedCart = cartService.addNewCartItem(currentCart,id,quantity);
        getSession().setAttribute("cart_items_list",updatedCart);
        getSession().setAttribute("total_price",productService.calculateTotalPrice(updatedCart));
        model.addAttribute("list",updatedCart);
        return "redirect:/";
    }
}
