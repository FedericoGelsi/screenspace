package com.uade.ad.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.ad.controller.dto.JwtResponseDto;
import com.uade.ad.service.RefreshTokenService;
import com.uade.ad.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        var user = userService.getUserByUsername(principal.getUsername());
        var userId = user.getId();
        var username = user.getUsername();
        var email = user.getEmail();
        String token = jwtUtils.createJwt(user.getEmail());
        //TODO REFRESH TOKEN
        //String refreshToken = refreshTokenService.createToken(user);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(objectMapper.writeValueAsString(JwtResponseDto.of(token,userId,username,email)));//, refreshToken)));
    }

}
