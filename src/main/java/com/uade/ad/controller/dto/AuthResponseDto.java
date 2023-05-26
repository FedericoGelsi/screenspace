package com.uade.ad.controller.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private final String token;

    public AuthResponseDto(String token) {
        this.token = token;
    }
}
