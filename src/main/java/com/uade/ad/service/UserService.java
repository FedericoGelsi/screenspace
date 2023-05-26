package com.uade.ad.service;

import com.uade.ad.model.User;
import com.uade.ad.repository.UserRepository;
import com.uade.ad.utils.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String email,String password){
        Optional<User> isUserCreated = userRepository.findUsersByEmail(email);
        if (isUserCreated.isPresent()) {
            throw new IllegalArgumentException("Email in use");
        }
        password = PasswordEncoder.encryptPassword(password);
        email = email.toLowerCase();
        User newUser = userRepository.save(new User(UUID.randomUUID().toString(), email, password));
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

