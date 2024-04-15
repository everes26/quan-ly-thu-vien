package com.service.auth.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Email is required")
    private String email;
}
