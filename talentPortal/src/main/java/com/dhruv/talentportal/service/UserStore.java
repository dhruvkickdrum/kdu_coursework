package com.dhruv.talentportal.service;


import com.dhruv.talentportal.model.User;
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
        // Seed = admin/admin123 . basic/basic123
        saveNewUser(new User("admin", "admin123", "admin@gmail.com", List.of("ROLE_ADMIN")));
        saveNewUser(new User("basic", "basic123", "basic@gmail.com", List.of("ROLE_BASIC")));
    }

    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(users.get(userName));
    }

    public List<User> findAll() {
        return users.values().stream()
                .map(u -> new User(u.getUserName(), u.getPassword(), u.getEmail(), u.getRoles()))
                .toList();
    }

    public User saveNewUser(User user) {
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        System.out.println("Encoded Password : "+ encoded);
        users.put(user.getUserName(), user);
        return user;
    }
}
