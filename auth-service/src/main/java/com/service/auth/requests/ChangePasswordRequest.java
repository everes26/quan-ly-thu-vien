package com.service.auth.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Old password is required")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
