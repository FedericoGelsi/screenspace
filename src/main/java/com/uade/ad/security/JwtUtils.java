package com.uade.ad.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.expiration}")
    private int expTime;

    @Value("${jwt.secret}")
    private String secret;

    public String createJwt(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli() + expTime))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean isJwtValid(String jwt) {
        try {
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(jwt);

            Instant expiration = Instant.ofEpochMilli(decodedJwt.getExpiresAt().getTime());
            Instant now = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
            return !now.isAfter(expiration);
        } catch (JWTVerificationException e) {
            // El JWT no es válido
            return false;
        }
    }
}

