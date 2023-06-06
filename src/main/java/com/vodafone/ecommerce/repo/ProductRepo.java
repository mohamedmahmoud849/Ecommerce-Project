package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategory(String category);

    Optional<Product> findByName(String name);

}

