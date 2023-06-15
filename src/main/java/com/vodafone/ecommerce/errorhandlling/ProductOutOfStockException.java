package com.vodafone.ecommerce.errorhandlling;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException() {
        super();
    }

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
