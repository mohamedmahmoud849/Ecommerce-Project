package com.vodafone.ecommerce.Security;

import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.UserRepository;
import com.vodafone.ecommerce.serviceImbl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailService mailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByUsername(username);
        if (user != null) {
                return new User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole())));
        }else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
