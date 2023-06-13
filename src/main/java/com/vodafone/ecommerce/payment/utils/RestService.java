package com.vodafone.ecommerce.payment.utils;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private String url = "http://localhost:8080/paymentservice/payment/pay";
    public PaymentResponse consumeRest(PaymentRequest paymentRequest){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<PaymentRequest> httpRequest = new HttpEntity<>(paymentRequest);
        return restTemplate.postForEntity(url,httpRequest,PaymentResponse.class).getBody();
    }
}
