package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImpl.ProductService;
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


    boolean isAuthenticated(){
        UserEntity user = userService.getCurrentLoggedInUser();
        return user != null;
    }

    boolean isUserAdmin(){
        UserEntity user = userService.getCurrentLoggedInUser();
        return user.getRole().equals("ADMIN");
    }
    @GetMapping("/")
    public ModelAndView showHomePage(Model model){
        if(!isAuthenticated())
            return new ModelAndView("login");
        else{
            if(isUserAdmin())
                return new ModelAndView("admin_home");
        }

        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        return new ModelAndView("new_home_Page_search","products",productService.getALl());
    }
    @GetMapping("/category/{category}")
    public ModelAndView showHomePageWithSaladMenu(@PathVariable("category") String category, Model model){
        model.addAttribute("customer_id",userService.getCurrentLoggedInUser().getId());
        model.addAttribute("category",category);
        return new ModelAndView("new_home_page","products",productService.getALlByCategory(category));
    }


}
