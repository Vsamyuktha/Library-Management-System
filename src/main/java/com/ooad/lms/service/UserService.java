package com.ooad.lms.service;

import com.ooad.lms.dao.UserRepository;
import com.ooad.lms.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
