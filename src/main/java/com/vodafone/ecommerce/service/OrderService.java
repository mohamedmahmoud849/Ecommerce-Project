package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    public Order insertNewOrder(List<Product> list){
        Long totalQuantity=0L,totalPrice=0L;

        for (Product product:
                list) {
            totalQuantity += product.getQuantity();
            totalPrice += Long.valueOf(product.getPrice()) * product.getQuantity();
        }
        return orderRepo.save(Order.builder()
                .orderDate(new Date())
                .itemsQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .build());

    }
}
