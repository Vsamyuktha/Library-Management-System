package com.ooad.lms.config;

import com.ooad.lms.service.AuthenticationHandlerService;
import com.ooad.lms.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final AuthenticationHandlerService authenticationHandlerService;

    public SecurityConfig(UserService userService, AuthenticationHandlerService authenticationHandlerService) {
        this.userService = userService;
        this.authenticationHandlerService = authenticationHandlerService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/login", "/unauthorized", "/css/**", "/js/**", "/images/**").permitAll() // Allow public access to these endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(authenticationHandlerService)
                        .failureUrl("/login?error")
                        .permitAll() // Allow access to login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll() // Allow access to logout success page
                )
                .csrf().disable(); // Disable CSRF for simplicity (not recommended for production)
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService) // Use UserService for authentication
                .passwordEncoder(passwordEncoder) // Use BCrypt for password encoding
                .and()
                .build();
    }
}
