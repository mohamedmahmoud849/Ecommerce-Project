package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserName(String username);
}
