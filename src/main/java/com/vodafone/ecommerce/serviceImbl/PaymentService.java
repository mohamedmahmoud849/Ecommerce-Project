package com.vodafone.ecommerce.serviceImbl;


import com.vodafone.ecommerce.errorhandlling.OrderNotFoundException;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.payment.stubs.ObjectFactory;
import com.vodafone.ecommerce.payment.stubs.ValidateCard;
import com.vodafone.ecommerce.payment.stubs.ValidateCardResponse;
import com.vodafone.ecommerce.payment.utils.PaymentRequest;
import com.vodafone.ecommerce.payment.utils.PaymentResponse;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.payment.utils.soapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final soapClient soapClient;
    private final RestService restService;
    private final UnConfirmedOrderService unConfirmedOrderService;
    private final ConfirmedOrderService confirmedOrderService;
    public String validateCard(ValidateCard validateCard){
        ObjectFactory objectFactory = new ObjectFactory();
        ValidateCardResponse response = soapClient.validateCard("http://localhost:9090/wsdl",objectFactory.createValidateCard(validateCard));
        return response.getResult();
    }
    public String validateCardDetails(Long cardNumber , Integer pin , String year , String month){
        ValidateCard card = new ValidateCard();
        card.setCardNumber(cardNumber);
        card.setPin(pin);
        String date = year+"-"+month+"-"+"28";
        card.setExpireDate(date);
        return validateCard(card);
    }



    public boolean isThereEnoughBalance(Long cardNumber){
        List<Product> currentOrderItems = unConfirmedOrderService.getCurentUserUnconfirmedOrderProductsList();
        if (currentOrderItems.isEmpty()){
            throw new OrderNotFoundException("You have No Orders To Be Purchased");
        }
        int amount = Integer.parseInt(unConfirmedOrderService.calculateOrderTotalPrice(currentOrderItems));
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardNumber(String.valueOf(cardNumber));
        paymentRequest.setAmountToBePaid(amount);
        PaymentResponse response = restService.checkCardBalance(paymentRequest);
        return response.getMessage().equals("There's Enough Balance");
    }

    public void consumeAmount(Long cardNumber){
        List<Product> currentOrderItems = unConfirmedOrderService.getCurentUserUnconfirmedOrderProductsList();
        if (currentOrderItems.isEmpty()){
            throw new OrderNotFoundException("You have No Orders To Be Purchased");
        }
        int amount = Integer.parseInt(unConfirmedOrderService.calculateOrderTotalPrice(currentOrderItems));
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardNumber(String.valueOf(cardNumber));
        paymentRequest.setAmountToBePaid(amount);
        restService.consumeAmountFromCard(paymentRequest);
    }

    public void completeCreditCardOrder(Long cardNumber){
        boolean checkResponse = isThereEnoughBalance(cardNumber);
        if (checkResponse){
            //consume items quantity
            confirmedOrderService.handleStock(unConfirmedOrderService.getCurentUserUnconfirmedOrderProductsList());
            //cosnume amount
            consumeAmount(cardNumber);
        }
        Order currentOrder = unConfirmedOrderService.getCurentUserUnconfirmedOrder();
        unConfirmedOrderService.confirmOrder(currentOrder.getId());
    }
    public void completeCashOrder(){
        //consume items quantity
        confirmedOrderService.handleStock(unConfirmedOrderService.getCurentUserUnconfirmedOrderProductsList());
        Order currentOrder = unConfirmedOrderService.getCurentUserUnconfirmedOrder();
        unConfirmedOrderService.confirmOrder(currentOrder.getId());
    }

}
