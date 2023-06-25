package com.uade.ad.service;

import com.uade.ad.controller.dto.UserUpdateDto;
import com.uade.ad.model.Role;
import com.uade.ad.model.User;
import com.uade.ad.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String email, String password, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email in use");
        }

        User newUser = User.builder().username(email.toLowerCase()).email(email.toLowerCase()).password(passwordEncoder.encode(password)).role(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER)).build();

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

