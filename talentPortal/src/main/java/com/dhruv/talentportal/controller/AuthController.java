package com.dhruv.talentportal.controller;


import com.dhruv.talentportal.dto.LoginRequestDTO;
import com.dhruv.talentportal.dto.LoginResponseDTO;
import com.dhruv.talentportal.security.AuditLogger;
import com.dhruv.talentportal.security.JwtService;
import com.dhruv.talentportal.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO reqDto) {
        var user = authService.authenticate(reqDto.getUserName(), reqDto.getPassword());
        String token = jwtService.generateToken(user.getUserName(), user.getRoles());

        auditLogger.loginSuccess(user.getUserName()); // Exercise 2 : Logging Requirements

        return ResponseEntity.ok(new LoginResponseDTO(token , user.getUserName(), user.getRoles()));
    }
}
