package com.vodafone.ecommerce.errorhandlling;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@ControllerAdvice
public class ApiExceptionHandler {
    /*@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException runtimeException){
        return "error";
    }
    @ExceptionHandler(ProductOutOfStockException.class)
    public String handleProductOutOfStockException(ProductOutOfStockException exception , Model model){
        String message = URLEncoder.encode(exception.getMessage(), StandardCharsets.ISO_8859_1);
        return "redirect:/cart?message=" + message;
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(OrderNotFoundException orderNotFoundException , Model model){
        model.addAttribute("message",orderNotFoundException.getMessage());
        return "error";
    }
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String handleNotFoundException(HttpClientErrorException.NotFound notFound , Model model){
        String message = notFound.getMessage().substring(19,notFound.getMessage().length() - 3);
        model.addAttribute("message", message);
        return "pay_by_card";
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleBadRequestException(MethodArgumentTypeMismatchException exception){
        return "error";
    }
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public String handleClientException(HttpClientErrorException.BadRequest exception){
        return "error";
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public String handleServerException(HttpServerErrorException exception ){
        return "error";
    }
    @ExceptionHandler(HttpClientErrorException.NotAcceptable.class)
    public String handleNotAcceptableException(HttpClientErrorException.NotAcceptable notAcceptable , Model model){
        String message = notAcceptable.getMessage().substring(19,notAcceptable.getMessage().length() - 3);
        model.addAttribute("message", message);
        return "pay_by_card";
    }
    @ExceptionHandler(RequestRejectedException.class)
    public String handleRequestRejectedException(RequestRejectedException exception ){
        return "error";
    }
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException exception){
        return "error";
    }

    private static String encryptMessage(String message, String secretKey) {
        try {
            byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt message", e);
        }
    }*/
}
