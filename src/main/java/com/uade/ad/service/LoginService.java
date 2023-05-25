package com.uade.ad.service;

import com.uade.ad.controller.dto.LoginDto;
import com.uade.ad.model.ScreenspaceTest;
import com.uade.ad.repository.DynamoDBRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    final private DynamoDBRepository dynamoDBRepository;

    public LoginService(DynamoDBRepository dynamoDBRepository) {
        this.dynamoDBRepository = dynamoDBRepository;
    }

    public String login(LoginDto dto) {
        return "null";
    }

    public String test() {
        ScreenspaceTest testt = ScreenspaceTest.builder()
                .cost(1).definition("hola").build();
        return dynamoDBRepository.save(testt);
    }
}