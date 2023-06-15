package com.vodafone.ecommerce.errorhandlling;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    /*@ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorDetails> handleApiException(APIException apiException ){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(apiException.getStatus().getReasonPhrase());
        errorDetails.setMessage(apiException.getMessage());
        return new ResponseEntity<>(errorDetails, apiException.getStatus());
    }*/
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException runtimeException , Model model){
        model.addAttribute("message", "We are sorry Try Again Later");
        return "error_page";
    }
    @ExceptionHandler(ProductOutOfStockException.class)
    public String handleProductOutOfStockException(ProductOutOfStockException exception , Model model){
        model.addAttribute("message", exception.getMessage());
        return "cart_page";
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
    @ExceptionHandler(HttpClientErrorException.NotAcceptable.class)
    public String handleNotFoundException(HttpClientErrorException.NotAcceptable notAcceptable , Model model){
        String message = notAcceptable.getMessage().substring(19,notAcceptable.getMessage().length() - 3);
        model.addAttribute("message", message);
        return "pay_by_card";
    }


    /*protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDetails.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }*/
}
