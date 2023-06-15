package com.vodafone.ecommerce.errorhandlling;

public class InternalException  extends RuntimeException{
    public InternalException() {
        super();
    }

    public InternalException(String message) {
        super(message);
    }
}
