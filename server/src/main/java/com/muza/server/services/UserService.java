package com.muza.server.services;

import com.muza.server.dto.UserResponse;
import com.muza.server.entities.User;
import com.muza.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(User user) {
        if (userRepository.existsByEmailHash(user.getEmailHash())) {
            throw new RuntimeException("Email already exists");
        }

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    public Optional<UserResponse> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserResponse::new);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<UserResponse> updateUser(Integer id, User userData) {
        return userRepository.findById(id).map(user -> {
            user.setDisplayName(userData.getDisplayName());

            userRepository.save(user);
            return new UserResponse(user);
        });
    }
}