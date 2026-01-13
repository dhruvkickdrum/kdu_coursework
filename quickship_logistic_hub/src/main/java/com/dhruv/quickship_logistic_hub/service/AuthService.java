package com.dhruv.quickship_logistic_hub.service;

import com.dhruv.quickship_logistic_hub.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserStore userStore, PasswordEncoder passwordEncoder) {
        this.userStore = userStore;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String userName, String rawPassword) {
        User user = userStore.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if(!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid Credentials");
        }
        return user;
    }

}
