package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.Security.SecurityUtil;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.*;
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

    private final ProductService productService;
    private final UserService userService;


    @GetMapping("/")
    public ModelAndView showHomePage(Model model){
        if(getSession().getAttribute("cart_items_list") == null){
            getSession().setAttribute("cart_items_list",new ArrayList<>());
        }
        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("search_test","products",productService.getALl());
    }
    @GetMapping("/salad")
    public ModelAndView showHomePageWithSaladMenu(Model model){
        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("home","products",productService.getALlByCategory("Salad"));
    }
    @GetMapping("/noodle")
    public ModelAndView showHomePageWithNoodleMenu(Model model){
        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("home","products",productService.getALlByCategory("Noodle"));
    }

    @GetMapping("/pizza")
    public ModelAndView showHomePageWithPizzaMenu(Model model){
        List<Product> currentCart =  (List<Product>) getSession().getAttribute("cart_items_list");
        model.addAttribute("cart_size",currentCart.size());
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("home","products",productService.getALlByCategory("Pizza"));
    }

}
