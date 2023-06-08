package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Role;
import com.vodafone.ecommerce.model.User;
import com.vodafone.ecommerce.repo.RoleRepo;
import com.vodafone.ecommerce.repo.UserRepo;
import jdk.jfr.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepo userRepository;
    private RoleRepo roleRepository;
   // private BCryptPasswordEncoder bCryptPasswordEncoder;
   @Lazy
   @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepository,
                       RoleRepo roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}
