package com.service.auth.controllers;

import com.service.auth.util.ContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class AuthorizationController {

    @Operation(description = "Check user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check user successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server Error")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/check")
    public ResponseEntity<?> checkAuthorization() {
        ContextUtil.getUserName();
        return ResponseEntity.ok().build();
    }
}
