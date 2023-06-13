package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.model.UserEntity;
import jakarta.mail.MessagingException;

public interface UserService {
    int MAX_FAILED_ATTEMPTS = 3;
    void saveUser(RegistrationDto registrationDto) throws MessagingException;
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    void activateUserAccount(RegistrationDto registrationDto);
    void increaseFailedAttempts(UserEntity user);
    void resetFailedAttempts(String email);
    void suspend(UserEntity user);

    UserEntity verifyState(Long id);

    void resetPassword(String newPassword,Long id);

    UserEntity getCurrentLoggedInUser();
}
