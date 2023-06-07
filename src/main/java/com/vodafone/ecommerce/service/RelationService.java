package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.relation.compositeKey;
import com.vodafone.ecommerce.repo.OrderRepo;
import com.vodafone.ecommerce.repo.ProductRepo;
import com.vodafone.ecommerce.repo.RelationRepo;
import com.vodafone.ecommerce.relation.relationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelationService {

    private final RelationRepo relationRepo;
    private final OrderRepo orderRepo;

    public void createRelations(List<Product> list, Order order){
        List<relationEntity> relations= new ArrayList<>();
        for (Product product:
                list) {
            relations.add(relationEntity.builder()
                    .id(compositeKey.builder().orderId(order.getId()).productId(product.getId()).build())
                    .quantity(product.getQuantity())
                    .order(order)
                    .product(product)
                    .build()
            );
        }
        relationRepo.saveAll(relations);
    }
}
