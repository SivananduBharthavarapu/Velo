package com.siva.velo.user.controller;

import com.siva.velo.common.response.ApiResponse;
import com.siva.velo.user.dto.LoginRequest;
import com.siva.velo.user.dto.RegisterUserRequest;
import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    @PostMapping("/login")
    public ApiResponse<UserResponse> loginUser(
            @Valid @RequestBody LoginRequest request) {

        UserResponse response = userService.loginUser(request);

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }
}