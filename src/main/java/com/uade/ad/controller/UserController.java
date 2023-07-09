package com.uade.ad.controller;

import com.uade.ad.controller.dto.NewUserDto;
import com.uade.ad.controller.dto.OAuthUserDto;
import com.uade.ad.controller.dto.UserUpdateDto;
import com.uade.ad.model.User;
import com.uade.ad.security.JwtUtils;
import com.uade.ad.service.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.findById(id);
            if (user.isEmpty()) return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/oauth")
    public ResponseEntity<?> googleAuthentication(@RequestBody String googleTokenId) {
        try {
            Triple<User, String, Boolean> result = userService.googleAuthentication(googleTokenId);
            return new ResponseEntity<>(
                    OAuthUserDto.builder()
                            .user(result.getLeft())
                            .jwt(result.getMiddle())
                            .isNewUser(result.getRight())
                            .build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error authenticating user: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid NewUserDto newUser) {
        try {
            User user = userService.createUser(newUser.getEmail(), newUser.getPassword(), newUser.getRole());
            return new ResponseEntity<>(user.toDto(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            boolean deleted = userService.deleteById(id);
            if (!deleted) return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userDto) {
        try {
            Optional<User> existingUser = userService.findById(id);
            if (existingUser.isEmpty()) return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            User updatedUser = userService.update(existingUser.get(), userDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
