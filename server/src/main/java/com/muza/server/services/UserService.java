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
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponse::new);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserResponse> updateUser(Long id, User userData) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(userData.getUsername());
            user.setEmail(userData.getEmail());

            // Хэшируем пароль если он не пустой и не null
            if (userData.getPassword() != null && !userData.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userData.getPassword()));
            }

            userRepository.save(user);
            return Optional.of(new UserResponse(user));
        }
        return Optional.empty();
    }
}
