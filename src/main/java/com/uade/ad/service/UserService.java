package com.uade.ad.service;

import com.uade.ad.model.Roles;
import com.uade.ad.model.User;
import com.uade.ad.repository.RoleRepository;
import com.uade.ad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String email, String password, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email in use");
        }

        Roles roles = roleRepository.findByName(role.toLowerCase()).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User newUser = User.builder()
                .idUser(UUID.randomUUID().toString())
                .email(email.toLowerCase())
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList(roles))
                .build();

        newUser = userRepository.save(newUser);
        return newUser.toDto();
    }

    public User findByUser(String email) {
        Optional<User> user = userRepository.findUsersByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        return user.get();
    }
}

