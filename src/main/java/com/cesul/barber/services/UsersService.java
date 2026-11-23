package com.cesul.barber.services;

import com.cesul.barber.dtos.UpdateAvatarDTO;
import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.dtos.UserResponseDTO;
import com.cesul.barber.entities.User;
import com.cesul.barber.mappers.UserMapper;
import com.cesul.barber.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UsersService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO create(UserCreationDTO user) {
        User entity = userMapper.toEntity(user);
        User saved = userRepository.save(entity);
        return userMapper.toResponseDTO(saved);
    }

    public List<UserResponseDTO> findAll() {
        return userMapper.toResponseDTOList(userRepository.findAll());
    }

    public Optional<UserResponseDTO> findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO);
    }

    public Optional<UserResponseDTO> update(UUID id, UserCreationDTO updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User existingUser = userOptional.get();
        userMapper.updateEntity(existingUser, updatedUser);
        User saved = userRepository.save(existingUser);
        return Optional.of(userMapper.toResponseDTO(saved));
    }

    public Optional<UserResponseDTO> updateAvatar(UUID id, UpdateAvatarDTO avatar) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User existingUser = userOptional.get();
        if (avatar.getAvatarUrl() != null) {
            existingUser.setAvatarUrl(avatar.getAvatarUrl());
        }
        User saved = userRepository.save(existingUser);
        return Optional.of(userMapper.toResponseDTO(saved));
    }

    public boolean delete(UUID id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public void clear() {
        userRepository.deleteAll();
    }
}
