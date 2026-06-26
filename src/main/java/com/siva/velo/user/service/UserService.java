package com.siva.velo.user.service;

import com.siva.velo.exception.InvalidCredentialsException;
import com.siva.velo.exception.UserAlreadyExistsException;
import com.siva.velo.user.dto.LoginRequest;
import com.siva.velo.user.dto.RegisterUserRequest;
import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private UserResponse mapToUserResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setBio(user.getBio());
        response.setProfileImage(user.getProfileImage());
        response.setStatus(user.getStatus());
        response.setIsOnline(user.getIsOnline());
        response.setLastSeen(user.getLastSeen());
        response.setCreatedAt(user.getCreatedAt());

        return response;
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

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setBio(savedUser.getBio());
        response.setProfileImage(savedUser.getProfileImage());
        response.setStatus(savedUser.getStatus());
        response.setIsOnline(savedUser.getIsOnline());
        response.setLastSeen(savedUser.getLastSeen());
        response.setCreatedAt(savedUser.getCreatedAt());

        return response;
    }
    public UserResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return mapToUserResponse(user);
    }
}