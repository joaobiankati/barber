package com.cesul.barber.mappers;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.dtos.UserResponseDTO;
import com.cesul.barber.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User toEntity(UserCreationDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAvatarUrl(dto.getAvatarUrl());
        return user;
    }

    public void updateEntity(User user, UserCreationDTO dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAvatarUrl(dto.getAvatarUrl());
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(user.getAvatarUrl());
        return dto;
    }

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
