
package com.uade.ad.service;

/*
import com.uade.ad.model.User;
import com.uade.ad.utils.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;

    public AuthService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public Jwt authenticate(User user, String password) {
        if(!PasswordEncoder.comparePasswords(password,user.getPassword())){
            throw new IllegalStateException("Invalid credentials");
        }
        return buildJwt(user);
    }

    private Jwt buildJwt(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(1200))
                .subject(user.getEmail())
                .claim("user", user.getId())
                //.claim("scope", user.getType().name())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }
}

*/