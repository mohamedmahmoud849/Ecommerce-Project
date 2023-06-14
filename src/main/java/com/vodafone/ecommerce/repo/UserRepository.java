package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String userName);
    UserEntity findFirstByUsername(String username);
    @Query(value = "update test.users set password=:newPassword where id=:id",nativeQuery = true)
    @Transactional
    @Modifying
    void updatePasswordById(@Param("newPassword") String newPassword,@Param("id") Long id);

    @Query(value="SELECT * FROM test.users WHERE role= 'ADMIN'", nativeQuery = true)
    List<UserEntity> findAllAdminUsers();
}
