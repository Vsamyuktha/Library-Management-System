package com.ooad.lms.controller;

import com.ooad.lms.entity.User;
import com.ooad.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") User user, Model model) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.saveUser(user);
            model.addAttribute("message", "Sign up successful!");
        } catch(DataIntegrityViolationException e) {
            model.addAttribute("message", "User already exists!");
        } catch (Exception e) {
            model.addAttribute("message", "Sign up failed. Try again!");
        }

        return "signup";
    }
}
