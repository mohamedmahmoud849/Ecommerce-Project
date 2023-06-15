package com.vodafone.ecommerce.errorhandlling;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
