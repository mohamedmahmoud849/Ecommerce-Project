package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.service.RelationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController extends BaseController{

    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;
    @RequestMapping("/cart")
    public String showCartPage(Model model) {
        model.addAttribute("items", getSession().getAttribute("cart_items_list"));
        model.addAttribute("total_price", getSession().getAttribute("total_price"));
        return "cart_page";
    }
    @RequestMapping("/payment")
    public String saveOrder(){
        List<Product> productsList = (List<Product>) getSession().getAttribute("cart_items_list");
        orderService.setOrderProductsRelation(productsList);
        if(!orderService.handleStock(productsList)){
            //TODO: handle this part to throw exception
            log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            log.info("no enough stock");
            log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        };
        getSession().invalidate();
        return "redirect:/";
    }


    @RequestMapping("/delete_cart_item/{name}")
    public String deleteCardItem(@PathVariable String name){
        List<Product> list = (List<Product>) getSession().getAttribute("cart_items_list");
        List<Product> newProductsList = cartService.deleteCardItem(list,name);
        getSession().setAttribute("cart_items_list", newProductsList);
        getSession().setAttribute("total_price",productService.calculateTotalPrice(newProductsList));
        return "redirect:/cart";
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
