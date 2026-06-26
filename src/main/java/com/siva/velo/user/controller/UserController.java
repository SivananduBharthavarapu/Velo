package com.siva.velo.user.controller;

import com.siva.velo.common.response.ApiResponse;
import com.siva.velo.user.dto.RegisterUserRequest;
import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
}