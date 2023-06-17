package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.serviceImpl.PaymentService;
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


    @PostMapping("/card")
    public String showCreditCardDataForm(@RequestParam("address") String address){
        getSession().setAttribute("address",address);
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
    @PostMapping("/cash")
    public String confirmOrderAndShowDeliveryPage(@RequestParam("address") String address){
        paymentService.completeCashOrder(address);
        return "order_delivery";
    }

    @GetMapping("/balance_consume")
    public String consumeAmountFromService(Model model){
        Long cardNumber = (Long) getSession().getAttribute("card_number");
        String address = (String) getSession().getAttribute("address");
        paymentService.completeCreditCardOrder(cardNumber,address);
        return "order_delivery";

    }


}
