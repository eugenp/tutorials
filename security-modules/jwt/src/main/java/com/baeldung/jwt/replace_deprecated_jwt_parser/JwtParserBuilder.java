package com.baeldung.jwt.replace_deprecated_jwt_parser;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtParserBuilder {

    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String token = Jwts.builder()
            .setSubject("username")
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(key)
            .compact();

        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

        Claims claims = parser.parseClaimsJws(token)
            .getBody();

        System.out.println("Claims: " + claims);
    }

}
