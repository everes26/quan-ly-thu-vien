package com.service.auth.services;

import com.service.auth.requests.ChangePasswordRequest;
import com.service.auth.requests.LoginRequest;
import com.service.auth.requests.RegisterRequest;
import com.service.auth.response.AuthResponse;
import com.service.auth.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

    UserResponse changePassword(ChangePasswordRequest setPasswordRequest);
}
