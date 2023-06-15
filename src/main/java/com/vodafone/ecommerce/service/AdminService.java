package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import org.apache.catalina.User;

import java.util.List;

public interface AdminService {
    void saveAdmin(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findById(Long id);

    void addAdmin(String email, String username, CharSequence password);

    void deleteAdmin(Long id);

    void updateAdmin(Long id, String username, String email, State state);

    List<UserEntity> getALlAdmins();
}
