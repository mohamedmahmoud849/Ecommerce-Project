package com.vodafone.ecommerce.serviceImbl;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
//import com.vodafone.ecommerce.repo.RoleRepository;
import com.vodafone.ecommerce.repo.UserRepository;
import com.vodafone.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        //this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveAdmin(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole("ADMIN");
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
    public UserEntity findById(Long id) {return userRepository.findById(id).get();}

    //TODO:: implement admin functions
    @Override
    public void addAdmin(String email, String username, CharSequence password) {

        userRepository.save(UserEntity.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .FailedLoggedIns(0)
                .role("ADMIN")
                .state(State.valueOf("INACTIVE"))
                .build());

    }


    @Override
    public void deleteAdmin(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateAdmin(Long id, String username, String email, State state) {

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
        return userRepository.findAllByRole("ADMIN");
    }




}
