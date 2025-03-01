package com.ooad.lms.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationHandlerService implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Get the role of the user
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        // Get the authenticated user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Fetch user ID (modify according to your UserDetails implementation)
        String username = ((User) userDetails).getUsername();
        // Redirect based on role
        if (isAdmin) {
            response.sendRedirect("/library/admin?username=" + username); // Redirect to admin dashboard
        } else  {
            response.sendRedirect("/library/user?username=" + username); // Redirect to user home page
        }
    }

}
