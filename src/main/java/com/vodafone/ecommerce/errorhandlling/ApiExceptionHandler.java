package com.vodafone.ecommerce.errorhandlling;


import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException runtimeException){
        return "error_page";
    }
    @ExceptionHandler(ProductOutOfStockException.class)
    public String handleProductOutOfStockException(ProductOutOfStockException exception , Model model){
        model.addAttribute("message", exception.getMessage());
        return "redirect:/cart";
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(OrderNotFoundException orderNotFoundException , Model model){
        model.addAttribute("message",orderNotFoundException.getMessage());
        return "error_page";
    }
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String handleNotFoundException(HttpClientErrorException.NotFound notFound , Model model){
        String message = notFound.getMessage().substring(19,notFound.getMessage().length() - 3);
        model.addAttribute("message", message);
        return "pay_by_card";
    }
    /*@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleBadRequestException(MethodArgumentTypeMismatchException exception){
        return "error_page";
    }*/
    @ExceptionHandler(HttpClientErrorException.class)
    public String handleClientException(HttpClientErrorException exception){
        return "error_page";
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public String handleServerException(HttpServerErrorException exception ){
        return "error_page";
    }
    @ExceptionHandler(HttpClientErrorException.NotAcceptable.class)
    public String handleNotAcceptableException(HttpClientErrorException.NotAcceptable notAcceptable , Model model){
        String message = notAcceptable.getMessage().substring(19,notAcceptable.getMessage().length() - 3);
        model.addAttribute("message", message);
        return "pay_by_card";
    }
    @ExceptionHandler(RequestRejectedException.class)
    public String handleRequestRejectedException(RequestRejectedException exception ){

        return "error_page";
    }

}
