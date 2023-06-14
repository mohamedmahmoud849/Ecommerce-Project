package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.model.UserEntity;
import org.apache.catalina.User;

public interface AdminService {
    void saveAdmin(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    void addAdmin(UserEntity user);
    void deleteAdmin(UserEntity user);

    void updateAdmin(UserEntity user);
    void showAllAdmins();
}