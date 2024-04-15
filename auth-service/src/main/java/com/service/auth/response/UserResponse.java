package com.service.auth.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String role;
    private boolean isActivated;
}
