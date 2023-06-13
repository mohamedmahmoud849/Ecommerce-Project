package com.vodafone.ecommerce.Security;

import com.vodafone.ecommerce.errorhandlling.NotFoundException;
import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repo.UserRepository;
import com.vodafone.ecommerce.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
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
