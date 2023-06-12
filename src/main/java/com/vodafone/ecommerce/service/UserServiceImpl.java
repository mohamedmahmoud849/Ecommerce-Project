package com.vodafone.ecommerce.service;


import com.vodafone.ecommerce.dto.RegistrationDto;
//import com.vodafone.ecommerce.model.Role;
import com.vodafone.ecommerce.model.UserEntity;
//import com.vodafone.ecommerce.repo.RoleRepository;
import com.vodafone.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;




    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
      //  this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setActive(false);
        user.setFailedLoggedIns(0);
        activateUserAccount(registrationDto);
        user.setRole("CUSTOMER");
        //Role role = roleRepository.findByName("USER");
        //user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void activateUserAccount(RegistrationDto registrationDto) {


        ///TODO:: Email verification

        //then user.setActive(true);
    }

    void updateFailedAttempts(int failAttempts, String email){
        UserEntity user = userRepository.findByEmail(email);
        user.setFailedLoggedIns(failAttempts);

        userRepository.save(user);
    }

    public void increaseFailedAttempts(UserEntity user) {
        int newFailAttempts = user.getFailedLoggedIns() + 1;
        updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    public void resetFailedAttempts(String email) {
        updateFailedAttempts(0, email);
    }

    public void lock(UserEntity user) {
        user.setActive(false);

        userRepository.save(user);
    }

    public boolean unlockWhenEmailVerified(UserEntity user) {
        //TODO:: unlock when email verified
        return false;
    }

}
