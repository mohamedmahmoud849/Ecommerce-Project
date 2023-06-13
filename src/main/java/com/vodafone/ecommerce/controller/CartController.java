package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.Security.SecurityUtil;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.payment.stubs.ValidateCard;
import com.vodafone.ecommerce.payment.utils.PaymentRequest;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController extends BaseController{


    private final CartService cartService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestService restService;
    private final UnConfirmedOrderService orderService;
    private final UserService userService;
    @RequestMapping("/cart")
    public String showCartPage(Model model) {
        List<Product> unconfirmedOrderProducts = new ArrayList<>();
        String unconfirmedOrderTotalPrice = "0";
        UserEntity loggedInCustomer = userService.getCurrentLoggedInUser();
        Order unconfirmedOrder = orderService.getUnconfirmedOrderByCustomerId(loggedInCustomer.getId());
        if(unconfirmedOrder!=null){
            //return List of Products and Total Price
            unconfirmedOrderProducts = orderService.getCardItemsForOrderDetails(unconfirmedOrder.getId());
            unconfirmedOrderTotalPrice = orderService.calculateTotalPrice(unconfirmedOrderProducts);
        }
        model.addAttribute("items", unconfirmedOrderProducts);
        model.addAttribute("total_price", unconfirmedOrderTotalPrice);
        return "cart_page";
    }
    @RequestMapping("/{id}")
    public String addNewItemToCart(@PathVariable Long id, @RequestParam("quantity") Integer quantity, Model model){
        log.info(String.valueOf(quantity));
        List<Product> unconfirmedOrderProducts = new ArrayList<>();
        List<Product> updatedOrder = new ArrayList<>();
        String unconfirmedOrderTotalPrice = "0";
        UserEntity loggedInCustomer = userService.getCurrentLoggedInUser();
        Order unconfirmedOrder = orderService.getUnconfirmedOrderByCustomerId(loggedInCustomer.getId());
        if(unconfirmedOrder!=null){
            unconfirmedOrderProducts = orderService.getCardItemsForOrderDetails(unconfirmedOrder.getId());
            unconfirmedOrderTotalPrice = orderService.calculateTotalPrice(unconfirmedOrderProducts);
            updatedOrder = orderService.addNewCartItem(unconfirmedOrderProducts,id,quantity);
            //delete past order data from db and add list of items in new order and set relations between them and save them all in db
            orderService.deleteUnconfirmedOrderTOUpdate(unconfirmedOrder.getId());
            orderService.setOrderProductsRelation(updatedOrder);
        }else{
            Product product = productService.getProductById(id);
            product.setQuantity(quantity);
            updatedOrder.add(product);
            unconfirmedOrderTotalPrice = orderService.calculateTotalPrice(updatedOrder);
            //add new list of items in new order and set relations between them and save them all in db
            orderService.setOrderProductsRelation(updatedOrder);
        }
        getSession().setAttribute("cart_items_list",updatedOrder);
        getSession().setAttribute("total_price",unconfirmedOrderTotalPrice);
        model.addAttribute("list",updatedOrder);
        return "redirect:/";
    }
   @RequestMapping("/payment")
    public String saveOrder(){
       UserEntity loggedInCustomer = userService.getCurrentLoggedInUser();
       Order unconfirmedOrder = orderService.getUnconfirmedOrderByCustomerId(loggedInCustomer.getId());
       if(unconfirmedOrder!=null){
           List<Product> unconfirmedOrderProducts = orderService.getCardItemsForOrderDetails(unconfirmedOrder.getId());
           String unconfirmedOrderTotalPrice = orderService.calculateTotalPrice(unconfirmedOrderProducts);
           ValidateCard validateCard = new ValidateCard();
           validateCard.setCardNumber(1425363785798658l);
           validateCard.setPin(1234);
           validateCard.setExpireDate("2024-01-23");
           if (paymentService.ValidateCard(validateCard).equals("Valid")){
               log.info(paymentService.ValidateCard(validateCard));
               log.info(unconfirmedOrderTotalPrice);
               PaymentRequest paymentRequest = PaymentRequest.builder()
                       .cardNumber("1234567890123456")
                       .amountToBePaid(5)
                       .build();
               if(restService.consumeRest(paymentRequest).getMessage().equals("Transaction Succeeded")){
                   log.info(restService.consumeRest(paymentRequest).getMessage());
                   orderService.confirmOrder(unconfirmedOrder.getId());
                   orderService.handleStock(unconfirmedOrderProducts);
               }
           }else {
               log.info(paymentService.ValidateCard(validateCard));
           }
       }
        getSession().removeAttribute("cart_items_list");
        getSession().removeAttribute("total_price");
        return "redirect:/";
    }

    @RequestMapping("/delete_cart_item/{name}")
    public String deleteCardItem(@PathVariable String name){
        UserEntity loggedInCustomer = userService.getCurrentLoggedInUser();
        Order unconfirmedOrder = orderService.getUnconfirmedOrderByCustomerId(loggedInCustomer.getId());
        orderService.deleteItemFromUnconfirmedOrderById(name,unconfirmedOrder.getId());
        orderService.updateOrder(unconfirmedOrder.getId());
        return "redirect:/cart";
    }
}
