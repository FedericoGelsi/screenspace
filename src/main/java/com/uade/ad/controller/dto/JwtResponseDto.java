package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponseDto {

    private String token;
    private String refreshToken;
    private Long userId;
    private String username;
    private String email;

    public JwtResponseDto(String token) {
        this.token = token;
    }

    public JwtResponseDto(String token, Long userId, String username,String email) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public JwtResponseDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public static JwtResponseDto of(String token, String refreshToken) {
        return new JwtResponseDto(token, refreshToken);
    }

    public static JwtResponseDto of(String token) {
        return new JwtResponseDto(token);
    }

    public static JwtResponseDto of(String token, Long userId, String username,String email) {
        return new JwtResponseDto(token,userId,username,email);
    }
}
