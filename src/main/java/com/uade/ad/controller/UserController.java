package com.uade.ad.controller;

import com.uade.ad.controller.dto.NewUserDto;
import com.uade.ad.model.User;
import com.uade.ad.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public User createUser(@RequestBody @Valid NewUserDto newUser) {
        return userService.createUser(newUser.getEmail(),newUser.getPassword());
    }

}
