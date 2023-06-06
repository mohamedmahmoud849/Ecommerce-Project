package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
