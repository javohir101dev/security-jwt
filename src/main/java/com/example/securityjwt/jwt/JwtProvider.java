package com.example.securityjwt.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    String secret = "maxfiySo'zBirnichi";

    public String generateToken(String username) {

        long experationTime = 1000 * 60 * 60 * 24;


        String generatedToken = Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + experationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .setSubject(username)
                .compact();

        return generatedToken;
    }


    public boolean isValidToken(String token) {

        try {
            Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getUsernameFromToken(String token) {

        String username = Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }


}
