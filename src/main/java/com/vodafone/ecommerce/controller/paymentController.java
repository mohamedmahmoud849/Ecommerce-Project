package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Mail;
import com.vodafone.ecommerce.payment.stubs.ObjectFactory;
import com.vodafone.ecommerce.payment.stubs.ValidateCard;
import com.vodafone.ecommerce.payment.stubs.ValidateCardResponse;
import com.vodafone.ecommerce.payment.utils.PaymentRequest;
import com.vodafone.ecommerce.payment.utils.PaymentResponse;
import com.vodafone.ecommerce.payment.utils.RestService;
import com.vodafone.ecommerce.payment.utils.soapClient;
import com.vodafone.ecommerce.service.MailService;
import com.vodafone.ecommerce.service.PaymentService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class paymentController {


    private final PaymentService paymentService;
    private final RestService restService;
    private final MailService mailService;
    @PostMapping("/validate")
    public String validateCard(@RequestBody ValidateCard validateCard){
        return paymentService.ValidateCard(validateCard);
    }
    @PostMapping("/consume")
    public PaymentResponse consumeAmount(@RequestBody PaymentRequest paymentRequest){
        return restService.consumeRest(paymentRequest);
    }

    /*@PostMapping("/verify")
    public ResponseEntity<String> verifyEmail() throws MessagingException {
        mailService.sendMail();
        return new ResponseEntity<>("Email Sent successfully", HttpStatus.OK);
    }*/
}
