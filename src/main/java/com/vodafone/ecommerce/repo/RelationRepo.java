package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.relation.relationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepo extends JpaRepository<relationEntity,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from test.relations where order_id =:orderId",nativeQuery = true)
    void deleteAllRelationByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query(value = "delete from test.relations where order_id =:orderId and product_id=:productId",nativeQuery = true)
    void deleteByOrderAndItemId(@Param("productId")Long productId,@Param("orderId") Long orderId);
}
