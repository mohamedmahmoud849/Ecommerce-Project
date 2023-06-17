package com.vodafone.ecommerce.Security;

import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.serviceImpl.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            if (user.getState()== State.ACTIVE) {
                if (user.getFailedLoggedIns() < userService.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.suspendUser(user);
                    try {
                        mailService.sendResetPasswordMail(user.getEmail(), user.getId());
                        exception = new LockedException("Your account has been locked due to 3 failed attempts."
                                + " Please Verify Yourself via email we have sent to you to reset your Password ");
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
