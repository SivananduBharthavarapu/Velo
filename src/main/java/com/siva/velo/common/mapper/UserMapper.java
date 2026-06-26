package com.siva.velo.common.mapper;

import com.siva.velo.user.dto.UserResponse;
import com.siva.velo.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {

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
}