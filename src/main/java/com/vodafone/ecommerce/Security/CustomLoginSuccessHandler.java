package com.vodafone.ecommerce.Security;

import com.vodafone.ecommerce.model.State;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = request.getParameter("username");
        UserEntity user = userService.findByUsername(username);

        if(user.getState()== State.ACTIVE){
            if (user.getFailedLoggedIns() > 0) {
                userService.resetFailedAttempts(user.getEmail());
            }
        }else{
            response.sendRedirect("/register/verify");
            return;
        }

        if(user.getRole().equals("ADMIN")){
            getRedirectStrategy().sendRedirect(request, response,"/admin_home");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
