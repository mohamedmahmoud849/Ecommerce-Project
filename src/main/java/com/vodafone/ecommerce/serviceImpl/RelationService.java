package com.vodafone.ecommerce.serviceImpl;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.relation.compositeKey;
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


    public void createRelations(List<Product> list, Order order){
        List<relationEntity> relations= new ArrayList<>();
        for (Product product:
                list) {
            if(product!=null){
                relations.add(relationEntity.builder()
                        .id(compositeKey.builder().orderId(order.getId()).productId(product.getId()).build())
                        .quantity(product.getQuantity())
                        .order(order)
                        .product(product)
                        .build()
                );
            }
        }
        relationRepo.saveAll(relations);
    }
    public void deleteAllRelationsWithOrderById(Long id){
        relationRepo.deleteAllRelationByOrderId(id);
    }

    public void deleteRelationBetweenItemAndOrder(Long productId, Long orderId) {
        relationRepo.deleteByOrderAndItemId(productId,orderId);
    }

    public void updateOrderItemRelation(Long orderId,Long itemId, Integer itemQuantity) {
        relationRepo.updateItemQuantityByOrderId(orderId,itemId,itemQuantity);
    }
}
