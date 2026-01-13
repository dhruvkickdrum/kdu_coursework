package com.dhruv.quickship_logistic_hub.service;

import com.dhruv.quickship_logistic_hub.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStore {
    private final PasswordEncoder passwordEncoder;
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UserStore(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void sendDemoUsers() {
        // Seed = manager/manager123 , driver/driver123
        saveNewUser(new User("manager", "manager123", List.of("ROLE_MANAGER")));
        saveNewUser(new User("driver", "driver123", List.of("ROLE_DRIVER")));
    }

    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public User saveNewUser(User user) {
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        users.put(user.getUsername(), user);
        return user;
    }
}
