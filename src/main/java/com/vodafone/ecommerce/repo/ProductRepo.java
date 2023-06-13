package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(String category);
    @Query(value = "select id as productId,quantity as productQuantity from product", nativeQuery = true)
    List<ItemsQuantityProjection> getAllProductsQuantity();

    @Transactional
    @Modifying
    @Query(value = "update product set quantity=:quantity where id=:id ",nativeQuery = true)
    void updateItemQuantityById(@Param("quantity") Integer quantity ,@Param("id") Long id);

    Product findByName(String name);
}

