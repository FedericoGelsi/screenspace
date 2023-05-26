package com.uade.ad.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date timeNow = new Date();
        Date expiration = new Date(timeNow.getTime() + SecurityConst.JWT_EXPIRATION_TOKEN);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,SecurityConst.JWT_FIRMA)
                .compact();
    }
    public String decodeUser(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConst.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) throws Exception {
        try{
             Jwts.parser().setSigningKey(SecurityConst.JWT_FIRMA).parseClaimsJws(token);
             return true;
        }
        catch (Exception e){
            throw new InvalidBearerTokenException("Invalid token.");
        }
    }
}















