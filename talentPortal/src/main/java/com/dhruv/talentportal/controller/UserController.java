package com.dhruv.talentportal.controller;

import com.dhruv.talentportal.dto.RegisterUserRequestDTO;
import com.dhruv.talentportal.model.User;
import com.dhruv.talentportal.service.UserStore;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStore userStore;


    public UserController(UserStore userStore) {
        this.userStore = userStore;
    }

    // basic and admin can view
    @GetMapping
    @PreAuthorize("hasAnyRole('BASIC', 'ADMIN')")
    public List<User> listUsers() {
        return userStore.findAll();
    }

    // admin can add new users
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@Valid @RequestBody RegisterUserRequestDTO req) {
        User saved = userStore.saveNewUser(
                new User(req.getUserName(), req.getPassword(), req.getEmail(), req.getRoles())
        );

        return ResponseEntity.ok(new User(saved.getUserName(), "********", saved.getEmail(), saved.getRoles()));
    }
}
