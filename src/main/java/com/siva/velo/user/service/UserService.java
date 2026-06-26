package com.siva.velo.user.service;

import com.siva.velo.exception.UserAlreadyExistsException;
import com.siva.velo.user.dto.RegisterUserRequest;
import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setPassword(request.getPassword());

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
}