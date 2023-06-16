package com.vodafone.ecommerce.payment.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private static final String USER_NAME = "LUCILLE";
    private static final String PASSWORD = "1234";

    public PaymentResponse checkCardBalance(PaymentRequest paymentRequest,String token){
        String url = "http://localhost:8081/payment/pay/check";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        HttpEntity<PaymentRequest> httpRequest = new HttpEntity<>(paymentRequest,headers);
        return restTemplate.postForEntity(url,httpRequest,PaymentResponse.class).getBody();
    }
    public PaymentResponse consumeAmountFromCard(PaymentRequest paymentRequest,String token){
        String url = "http://localhost:8081/payment/pay/consume";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        HttpEntity<PaymentRequest> httpRequest = new HttpEntity<>(paymentRequest,headers);
        return restTemplate.postForEntity(url,httpRequest,PaymentResponse.class).getBody();
    }
    public AuthenticationResponse sendAuthenticateRequest(){
        String url = "http://localhost:8081/auth/authenticate";
        AuthenticationRequest request = AuthenticationRequest.builder()
                                        .username(USER_NAME)
                                        .password(PASSWORD)
                                        .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<AuthenticationRequest> httpEntity = new HttpEntity<>(request);
        AuthenticationResponse response = restTemplate.postForEntity(url,httpEntity,AuthenticationResponse.class).getBody();
        return response;
    }
}
