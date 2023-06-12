package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.model.UserEntity;

public interface UserService {
    int MAX_FAILED_ATTEMPTS = 3;
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    void activateUserAccount(RegistrationDto registrationDto);
    void increaseFailedAttempts(UserEntity user);
    void resetFailedAttempts(String email);
    void lock(UserEntity user);
    boolean unlockWhenEmailVerified(UserEntity user);
}
