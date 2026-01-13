package com.dhruv.quickship_logistic_hub.controller;

import com.dhruv.quickship_logistic_hub.dto.LoginRequestDto;
import com.dhruv.quickship_logistic_hub.dto.LoginResponseDto;
import com.dhruv.quickship_logistic_hub.security.AuditLogger;
import com.dhruv.quickship_logistic_hub.security.JwtService;
import com.dhruv.quickship_logistic_hub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final AuditLogger auditLogger;


    public AuthController(AuthService authService, JwtService jwtService, AuditLogger auditLogger) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.auditLogger = auditLogger;
    }

    // Controller to login the user , weather is it manager or the driver.
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@Valid @RequestBody LoginRequestDto reqDto) {
        var user = authService.authenticate(reqDto.getUserName(), reqDto.getPassword());
        String token = jwtService.generateToken(user.getUsername(), user.getRoles());
        auditLogger.loginSuccess(user.getUsername());
        return ResponseEntity.ok(new LoginResponseDto(token, user.getUsername(), user.getRoles()));
    }
}
