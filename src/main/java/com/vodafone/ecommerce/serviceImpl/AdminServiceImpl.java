package com.vodafone.ecommerce.serviceImpl;

import com.vodafone.ecommerce.errorhandlling.EmailAlreadyExistsException;
import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
//import com.vodafone.ecommerce.repo.RoleRepository;
import com.vodafone.ecommerce.repo.UserRepository;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository,  PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public UserEntity findById(Long id) {return userRepository.findById(id).get();}

    //TODO:: implement admin functions
    @Override
    public void addAdmin(String email, String username, CharSequence password) {

        validateEmail(email);
        validateUsername(username);
        userRepository.save(UserEntity.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .FailedLoggedIns(0)
                .role("ADMIN")
                .state(State.ACTIVE)
                .build());

    }


    @Override
    public void deleteAdmin(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateAdmin(Long id, String username, String email, State state) {

        validateEmailForUpdate(email,id);
        validateUsernameForUpdate(username,id);
        UserEntity user = userRepository.findById(id).get();
        user.setEmail(email);
        user.setUsername(username);
        user.setState(state);
        user.setFailedLoggedIns(user.getFailedLoggedIns());
        user.setId(id);
        userRepository.save(user);
    }
    @Override
    public List<UserEntity> getALlAdmins() {
        UserEntity user = userService.getCurrentLoggedInUser();
        return userRepository.findAllByRole(user.getId());
    }
 /*   public List<UserEntity> getALlOtherAdmins() {
        UserEntity user = userService.findByEmail(getSessionUser());

//        UserEntity user = userServiceImpl.getCurrentLoggedInUser();
        List<UserEntity> list = userRepository.findAllByRole(user.getId());
        return list;
    }*/

    @Override
    public void validateEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyExistsException("This email is already registered.");
        }
    }
    @Override
    public void validateEmailForUpdate(String email, Long id) {

        if (userRepository.findByEmail(email) != null && !Objects.equals(email, userRepository.findById(id).get().getEmail())) {
            throw new EmailAlreadyExistsException("This email is already registered.");
        }
    }

    @Override
    public void validateUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            throw new EmailAlreadyExistsException("This username is already registered.");
        }
    }

    @Override
    public void validateUsernameForUpdate(String username, Long id) {

        if (userRepository.findByUsername(username) != null && !Objects.equals(username, userRepository.findById(id).get().getUsername())) {
            throw new EmailAlreadyExistsException("This username is already registered.");
        }
    }
}
