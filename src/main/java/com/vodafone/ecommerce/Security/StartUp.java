package com.vodafone.ecommerce.Security;

import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements CommandLineRunner {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Autowired
    public StartUp(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Store one admin before start up
        UserEntity existingUserEmail = userRepository.findByEmail("fawzy@gmail.com");
        if(existingUserEmail == null) {
            UserEntity firstAdmin = new UserEntity();
            firstAdmin.setUsername("fawzy");
            firstAdmin.setEmail("fawzy@gmail.com");
            firstAdmin.setPassword(passwordEncoder.encode("1234"));
            firstAdmin.setState(State.ACTIVE);
            firstAdmin.setRole("ADMIN");
            firstAdmin.setFailedLoggedIns(0);
            userRepository.save(firstAdmin);
        }
    }
}
