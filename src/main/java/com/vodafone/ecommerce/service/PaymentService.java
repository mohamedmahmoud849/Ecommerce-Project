package com.vodafone.ecommerce.service;


import com.vodafone.ecommerce.payment.stubs.ObjectFactory;
import com.vodafone.ecommerce.payment.stubs.ValidateCard;
import com.vodafone.ecommerce.payment.stubs.ValidateCardResponse;
import com.vodafone.ecommerce.payment.utils.soapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class PaymentService {
    private final soapClient soapClient;
    public String ValidateCard(ValidateCard validateCard){
        ObjectFactory objectFactory = new ObjectFactory();
        ValidateCardResponse response = soapClient.validateCard("http://localhost:9090/wsdl",objectFactory.createValidateCard(validateCard));
        return response.getResult();
    }
}
