package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.Projection;

import java.util.List;

public interface ConfirmedOrderService {

    List<Order> getAllOrdersByCustomerId(Long id);

    Order addNewOrder(List<Product> list);

    void setOrderProductsRelation(List<Product> productsList);

    Order getOrderDetails(Long id);

    List<Projection> getProjection(Long id);

    List<Product> getProductsForOrderDetails(List<Projection> list);

    List<Product> getCardItemsForOrderDetails(List<Projection> list);

    void handleStock(List<Product> products);

    void updateProductQuantity(Integer quantity, Long id);
}
