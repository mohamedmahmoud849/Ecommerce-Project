package com.vodafone.ecommerce.serviceImbl;


import com.vodafone.ecommerce.Security.SecurityUtil;
import com.vodafone.ecommerce.dto.RegistrationDto;
//import com.vodafone.ecommerce.model.Role;
import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
//import com.vodafone.ecommerce.repo.RoleRepository;
import com.vodafone.ecommerce.repo.UserRepository;
import com.vodafone.ecommerce.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;




    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,MailService mailService) {
        this.userRepository = userRepository;
      //  this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService=mailService;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) throws MessagingException {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setState(State.INACTIVE);
        user.setFailedLoggedIns(0);
        //activateUserAccount(registrationDto);
        user.setRole("CUSTOMER");
        //Role role = roleRepository.findByName("USER");
        //user.setRoles(Collections.singletonList(role));
        UserEntity newSavedUser = userRepository.save(user);
        mailService.sendVerifyMail(newSavedUser.getEmail(), newSavedUser.getId());
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

    @Override
    public void suspend(UserEntity user) {
        user.setState(State.SUSPENDED);
        userRepository.save(user);
    }


    @Override
    public UserEntity verifyState(Long id) {
        UserEntity user = userRepository.findById(id).get();
        user.setState(State.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public void resetPassword(String newPassword,Long id) {
        UserEntity user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setState(State.ACTIVE);
        user.setFailedLoggedIns(0);
        userRepository.save(user);
    }
    @Override
    public UserEntity getCurrentLoggedInUser(){
        return findByEmail(SecurityUtil.getSessionUser());
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

}
