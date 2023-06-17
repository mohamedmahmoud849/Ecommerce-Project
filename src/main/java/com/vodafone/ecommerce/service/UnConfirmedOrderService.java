package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.Projection;

import java.util.List;

public interface UnConfirmedOrderService {
    Order getCurentUserUnconfirmedOrder();
    List<Product> getCurentUserUnconfirmedOrderProductsList();
    List<Projection> getProjection(Long orderId);
    List<Product> getProductsForOrderDetails(List<Projection> list);
    List<Product> getCartItemsForOrderDetails(Long orderId);
    void addNewCartItem(Long id , Integer quantity);
    Order addNewUnconfirmedOrder(List<Product> list);
    void setOrderProductsRelation(List<Product> productsList);
    String calculateOrderTotalPrice(List<Product> productsList);
    void deleteUnconfirmedOrderTOUpdate(Long id);
    void confirmOrder(Long id);
    void deleteItemFromUnconfirmedOrderById(String name);
}
