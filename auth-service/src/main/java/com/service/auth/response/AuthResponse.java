package com.service.auth.response;

import com.service.auth.entitys.UserEntity;
import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";
    private Long id;
    private String username;
    private String email;

    public AuthResponse(String accessToken, UserEntity user) {
        this.accessToken = accessToken;
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
