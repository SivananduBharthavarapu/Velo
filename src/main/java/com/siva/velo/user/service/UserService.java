package com.siva.velo.user.service;

import com.siva.velo.common.mapper.UserMapper;
import com.siva.velo.exception.InvalidCredentialsException;
import com.siva.velo.exception.UserAlreadyExistsException;
import com.siva.velo.exception.UserNotFoundException;
import com.siva.velo.security.JwtService;
import com.siva.velo.user.dto.LoginRequest;
import com.siva.velo.user.dto.LoginResponse;
import com.siva.velo.user.dto.RegisterUserRequest;
import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserMapper userMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    public UserResponse registerUser(RegisterUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                userMapper.toUserResponse(user)
        );
    }

    public UserResponse getCurrentUser(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return userMapper.toUserResponse(user);
    }
}