package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.service.RelationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController extends BaseController{

    private final OrderService orderService;
    private final RelationService relationService;
    @RequestMapping("/cart")
    public String cartPage(Model model) {
        model.addAttribute("items", getSession().getAttribute("cart_items_list"));
        model.addAttribute("total_price", getSession().getAttribute("total_price"));
        return "card_page";
    }
    @RequestMapping("/payment")
    public String payment(){
        //making order and set relations to it
        List<Product> list = (List<Product>) getSession().getAttribute("cart_items_list");
        log.info(String.valueOf(list.size()));
        Order newOrder = orderService.insertNewOrder(list);
        relationService.createRelations(list,newOrder);
        getSession().invalidate();
        return "redirect:/";
    }
}
