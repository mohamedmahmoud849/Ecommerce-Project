package com.vodafone.ecommerce.payment.utils;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    public PaymentResponse checkCardBalance(PaymentRequest paymentRequest){
        String url = "http://localhost:8080/paymentservice/payment/pay/check";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<PaymentRequest> httpRequest = new HttpEntity<>(paymentRequest);
        return restTemplate.postForEntity(url,httpRequest,PaymentResponse.class).getBody();
    }
    public PaymentResponse consumeAmountFromCard(PaymentRequest paymentRequest){
        String url = "http://localhost:8080/paymentservice/payment/pay/consume";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<PaymentRequest> httpRequest = new HttpEntity<>(paymentRequest);
        return restTemplate.postForEntity(url,httpRequest,PaymentResponse.class).getBody();
    }
}
