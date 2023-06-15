package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.payment.utils.PaymentResponse;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.serviceImbl.ConfirmedOrderService;
import com.vodafone.ecommerce.serviceImbl.PaymentService;
import com.vodafone.ecommerce.serviceImbl.UnConfirmedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
@Slf4j
public class paymentController extends BaseController {


    private final PaymentService paymentService;


    @GetMapping("/credit_card_data")
    public String showCreditCardDataForm(){
        return "pay_by_card";
    }
    @PostMapping("/credit_card_data")
    public String validateCardData(@RequestParam("credit-card-number") Long cardNumber,
                                   @RequestParam("pin-number")  Integer pin,
                                   @RequestParam("expiration-month") String expireMonth,
                                   @RequestParam("expiration-year") String expireYear, Model model)
    {
        String soapResponse = paymentService.validateCardDetails(cardNumber,pin,expireYear,expireMonth);
        if (soapResponse.equals("Valid")){
            getSession().setAttribute("card_number",cardNumber);
            return "redirect:/payment/balance_consume";
        }
        model.addAttribute("message",soapResponse);
        return "pay_by_card";
    }
    @GetMapping("/cash")
    public String confirmOrderAndShowDeliveryPage(){
        paymentService.completeCashOrder();
        return "order_delivery";
    }

    @GetMapping("/balance_consume")
    public String consumeAmountFromService(Model model){
        Long cardNumber = (Long) getSession().getAttribute("card_number");
        paymentService.completeCreditCardOrder(cardNumber);
        return "order_delivery";

    }


}
