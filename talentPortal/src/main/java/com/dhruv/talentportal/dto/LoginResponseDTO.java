package com.dhruv.talentportal.dto;

import java.util.List;

public class LoginResponseDTO {
    private String token;
    private String tokenType = "Bearer";
    private String userName;
    private List<String> roles;


    public LoginResponseDTO(String token, String userName, List<String> roles) {
        this.token = token;
        this.userName = userName;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getRoles() {
        return roles;
    }
}
