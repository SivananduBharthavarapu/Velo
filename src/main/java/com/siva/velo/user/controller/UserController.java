package com.siva.velo.user.controller;

import com.siva.velo.common.response.ApiResponse;
import com.siva.velo.user.dto.LoginRequest;
import com.siva.velo.user.dto.LoginResponse;
import com.siva.velo.user.dto.RegisterUserRequest;
import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "User Management",
        description = "APIs for user registration, authentication and profile"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new VELO user account."
    )
    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(
            @Valid @RequestBody RegisterUserRequest request) {

        UserResponse response = userService.registerUser(request);

        return new ApiResponse<>(
                true,
                "User registered successfully",
                response
        );
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates a user and returns a JWT token."
    )
    @PostMapping("/login")
    public ApiResponse<LoginResponse> loginUser(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.loginUser(request);

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }

    @Operation(
            summary = "Get current user",
            description = "Returns the authenticated user's profile."
    )
    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(Authentication authentication) {

        UserResponse response = userService.getCurrentUser(authentication);

        return new ApiResponse<>(
                true,
                "Current user fetched successfully",
                response
        );
    }
}