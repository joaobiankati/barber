package com.cesul.barber.service;

import com.cesul.barber.dtos.UserCreationDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    private List<UserCreationDTO> users = new ArrayList<>();

    public UserCreationDTO create(UserCreationDTO user) {
        user.setId(UUID.randomUUID());
        this.users.add(user);

        return user;
    }

    public List<UserCreationDTO> getAllUsers() {
        return this.users;
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        for (UserCreationDTO user : this.users) {
            if (user.getId().equals(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO request) {
        Optional<UserCreationDTO> userOptional = findById(id);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        UserCreationDTO user = userOptional.get();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setAvatarUrl(request.getAvatarUrl());

        return Optional.of(user);
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, Map<String, Object> request) {
        Optional<UserCreationDTO> userOptional = findById(id);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        UserCreationDTO user = userOptional.get();
        Object avatarUrl = request.get("avatarUrl");
        if (avatarUrl != null) {
            user.setAvatarUrl(avatarUrl.toString());
        }

        return Optional.of(user);
    }

    public boolean delete(UUID id) {
        Optional<UserCreationDTO> userOptional = findById(id);
        if (userOptional.isEmpty()) {
            return false;
        }

        this.users.remove(userOptional.get());
        return true;
    }

    public boolean clear() {
        this.users.clear();
        return true;
    }
}
