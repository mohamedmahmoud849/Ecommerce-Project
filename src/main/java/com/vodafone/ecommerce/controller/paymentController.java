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

    /*@RequestMapping("/payment")
    public String purchaseOrder(){
        Order unconfirmedOrder = orderService.getCurentUserUnconfirmedOrder();
        if(unconfirmedOrder!=null){
            List<Product> unconfirmedOrderProducts = orderService.getCardItemsForOrderDetails(unconfirmedOrder.getId());
            String unconfirmedOrderTotalPrice = orderService.calculateOrderTotalPrice(unconfirmedOrderProducts);
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

        return "redirect:/";
    }*/


/*
    @PostMapping("/validate")
    public String validateCard(@RequestBody ValidateCard validateCard){
        return paymentService.ValidateCard(validateCard);
    }
    @PostMapping("/consume")
    public PaymentResponse consumeAmount(@RequestBody PaymentRequest paymentRequest){
        return restService.consumeRest(paymentRequest);
    }
*/

}
