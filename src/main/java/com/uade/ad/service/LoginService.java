package com.uade.ad.service;

import com.uade.ad.controller.dto.LoginDto;
import com.uade.ad.model.ScreenspaceTest;
import com.uade.ad.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {

    final private LoginRepository loginRepository;

    public LoginService(LoginRepository dynamoDBRepository) {
        this.loginRepository = dynamoDBRepository;
    }

    public String login(LoginDto dto) {
        return "null";
    }

    public String test() {
        ScreenspaceTest testt = ScreenspaceTest.builder()
                .cost(1).definition("hola").instant(Instant.now()).build();
        ScreenspaceTest saved = loginRepository.save(testt);
        return saved.getId().toString();
    }
}