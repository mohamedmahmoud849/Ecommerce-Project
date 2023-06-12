package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.payment.stubs.ValidateCard;
import com.vodafone.ecommerce.payment.utils.PaymentRequest;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.service.*;
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
    private final PaymentService paymentService;
    private final RestService restService;
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
        ValidateCard validateCard = new ValidateCard();
        validateCard.setCardNumber(1425363785798658l);
        validateCard.setPin(1234);
        validateCard.setExpireDate("2024-01-23");
        if (paymentService.ValidateCard(validateCard).equals("Valid")){
            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .cardNumber("1234567890123456")
                    .amountToBePaid(5)
                    .build();
            if(restService.consumeRest(paymentRequest).getMessage().equals("Transaction Succeeded")){
                orderService.handleStock(productsList);
            }
        }else {
            log.info(paymentService.ValidateCard(validateCard));
        }
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
