package com.uade.ad.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.uade.ad.controller.dto.UserUpdateDto;
import com.uade.ad.model.Role;
import com.uade.ad.model.User;
import com.uade.ad.repository.UserRepository;
import com.uade.ad.security.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleIdTokenVerifier googleVerifier;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils)
            throws GeneralSecurityException, IOException {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.googleVerifier = new GoogleIdTokenVerifier
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("954758489890-k2vos57qjf8nk1hrqpaeee7us9p65l4u.apps.googleusercontent.com"))
                .build();
    }


    public Triple<User, String, Boolean> googleAuthentication(String googleTokenId) {
        try {
            GoogleIdToken token = googleVerifier.verify(googleTokenId);
            GoogleIdToken.Payload payload = token.getPayload();
            Optional<User> optional = userRepository.findUserByEmail(payload.getEmail());
            User user = null;
            if (optional.isEmpty()) {
                User newUser = User.builder()
                        .username(payload.getEmail())
                        .email(payload.getEmail())
                        .avatar((String) payload.get("picture"))
                        .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                        .build();
                user = userRepository.save(newUser);
            } else {
                user = optional.get();
            }
            boolean isNewUser = optional.isEmpty();
            return Triple.of(user, jwtUtils.createJwt(user.getEmail()), isNewUser);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User createUser(String email, String password, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email in use");
        }

        User newUser = User.builder()
                .username(email.toLowerCase())
                .email(email.toLowerCase())
                .password(passwordEncoder.encode(password))
                .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                .build();

        newUser = userRepository.save(newUser);
        return newUser.toDto();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found by email!"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found by username!"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findUsersByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        return user.get();
    }

    public void changePassword(String email, String newPassword) {
        Optional<User> user = userRepository.findUsersByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user.get());
    }

    public boolean deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(User user, UserUpdateDto userDto) {
        BeanUtils.copyProperties(userDto,user);
        return userRepository.save(user);
    }
}

