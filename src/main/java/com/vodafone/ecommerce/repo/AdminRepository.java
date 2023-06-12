package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM test.users", nativeQuery = true)
    public List<UserEntity> showAllUsers();
}
