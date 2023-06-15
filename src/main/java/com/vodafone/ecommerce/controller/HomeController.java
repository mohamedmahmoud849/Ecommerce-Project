package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImbl.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController extends BaseController{

    private final ProductService productService;
    private final UserService userService;


    @GetMapping("/")
    public ModelAndView showHomePage(Model model){
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("search_test","products",productService.getALl());
    }
    @GetMapping("/category/{category}")
    public ModelAndView showHomePageWithSaladMenu(@PathVariable("category") String category, Model model){
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("new_home_page","products",productService.getALlByCategory(category));
    }


}
