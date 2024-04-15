package com.service.auth.services.impl;

import com.service.auth.requests.ChangePasswordRequest;
import com.service.auth.requests.LoginRequest;
import com.service.auth.requests.RegisterRequest;
import com.service.auth.response.AuthResponse;
import com.service.auth.entitys.Role;
import com.service.auth.entitys.UserEntity;
import com.service.auth.enums.ErrorCode;
import com.service.auth.enums.RoleSystem;
import com.service.auth.exception.BasicException;
import com.service.auth.repository.RoleRepository;
import com.service.auth.repository.UserRepository;
import com.service.auth.response.UserResponse;
import com.service.auth.security.JWTGenerator;
import com.service.auth.services.UserService;
import com.service.auth.util.MessageConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        this.validateRegister(registerRequest);

        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());

        Role role = roleRepository.findByName(RoleSystem.ROLE_USER)
                .orElseThrow(() -> BasicException.builder()
                        .code(ErrorCode.NOT_FOUND.getCode())
                        .message(MessageConst.ROLE_NOT_FOUND)
                        .errors(Collections.singletonList(MessageConst.A0001))
                        .build());

        user.setRoles(Collections.singletonList(role));

        UserEntity userSaved = userRepository.save(user);
        UserResponse response = this.asUserResponse(userSaved);

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> BasicException.builder()
                .code(ErrorCode.NOT_FOUND.getCode())
                .message(MessageConst.USER_NOT_FOUND)
                .errors(Collections.singletonList(MessageConst.A0002))
                .build());

        if (!user.isActivated()) {
            throw BasicException.builder()
                    .code(ErrorCode.BAD_REQUEST.getCode())
                    .message(MessageConst.USER_NOT_ACTIVATED)
                    .errors(Collections.singletonList(MessageConst.A0003))
                    .build();
        }

        return new AuthResponse(token, user);
    }

    @Override
    public UserResponse changePassword(ChangePasswordRequest setPasswordRequest) {
        UserEntity user = userRepository.findByUsername(setPasswordRequest.getUsername()).orElseThrow(() -> BasicException.builder()
                .code(ErrorCode.NOT_FOUND.getCode())
                .message(MessageConst.USER_NOT_FOUND)
                .errors(Collections.singletonList(MessageConst.A0002))
                .build());

        if (!passwordEncoder.matches(setPasswordRequest.getOldPassword(), user.getPassword())) {
            throw BasicException.builder()
                    .code(ErrorCode.BAD_REQUEST.getCode())
                    .message(MessageConst.OLD_PASSWORD_INCORRECT)
                    .errors(Collections.singletonList(MessageConst.A0006))
                    .build();
        }

        user.setPassword(passwordEncoder.encode(setPasswordRequest.getNewPassword()));
        userRepository.save(user);
        return this.asUserResponse(user);
    }

    private UserResponse asUserResponse(UserEntity user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRoles().stream().map(Role::getName).map(Enum::name).collect(Collectors.joining(",")))
                .isActivated(user.isActivated())
                .build();
    }

    private void validateRegister(RegisterRequest registerRequest) {
        if (StringUtils.isBlank(registerRequest.getUsername())
                || StringUtils.isBlank(registerRequest.getPassword())
                || StringUtils.isBlank(registerRequest.getUsername()))
            throw BasicException.builder()
                    .code(ErrorCode.BAD_REQUEST.getCode())
                    .message("Username, Password and Email cannot be blank")
                    .errors(Collections.singletonList("Username, Password and Email cannot be blank"))
                    .build();

        if (Boolean.TRUE.equals(userRepository.existsByUsername(registerRequest.getUsername())))
            throw BasicException.builder()
                    .code(ErrorCode.BAD_REQUEST.getCode())
                    .message("Username already registered")
                    .errors(Collections.singletonList("Username " + registerRequest.getUsername() + " is already registered"))
                    .build();

        if (Boolean.TRUE.equals(userRepository.existsByEmail(registerRequest.getEmail())))
            throw BasicException.builder()
                    .code(ErrorCode.BAD_REQUEST.getCode())
                    .message("Email already registered")
                    .errors(Collections.singletonList("Email " + registerRequest.getEmail() + " is already registered"))
                    .build();
    }


    private String generateActivationCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = (int) (chars.length() * Math.random());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }
}
