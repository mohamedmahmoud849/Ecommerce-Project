package com.vodafone.ecommerce.repo;

public interface Projection {
    Long getItemId();
    String getName();
    String getPrice();
    Long getQuantity();

    String getImage();
}
