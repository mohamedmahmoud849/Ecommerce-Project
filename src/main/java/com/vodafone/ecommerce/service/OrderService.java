package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;

import java.util.List;

public interface OrderService {
    Order addNewOrder(List<Product> list);
    List<Product> getCartItemsForOrderDetails(Long orderId);
}
