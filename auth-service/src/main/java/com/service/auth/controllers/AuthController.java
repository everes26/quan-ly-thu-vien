package com.service.auth.controllers;

import com.service.auth.requests.ChangePasswordRequest;
import com.service.auth.requests.LoginRequest;
import com.service.auth.requests.RegisterRequest;
import com.service.auth.response.AuthResponse;
import com.service.auth.response.UserResponse;
import com.service.auth.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Login account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login account successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server Error")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @Operation(description = "Register account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register account successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server Error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.OK);
    }

    // api change password
    @Operation(description = "Change password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change password successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server Error")
    })
    @PostMapping("/change-password")
    public ResponseEntity<UserResponse> changePassword(@Valid @RequestBody ChangePasswordRequest setPasswordRequest) {
        return new ResponseEntity<>(userService.changePassword(setPasswordRequest), HttpStatus.OK);
    }

}
