package com.baeldung.jwt.replace_deprecated_jwt_parser;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

class DeprecatedParserUnitTest {

    private SecretKey key;
    private String token;

    @BeforeEach
    public void setup() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        token = Jwts.builder()
            .setSubject("baeldung")
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(key)
            .compact();
    }

    @Test
    void givenDeprecatedParser_whenParsingTokenInMultipleThreads_thenMayNotBeThreadSafe() {

        JwtParser parser = Jwts.parser()
            .setSigningKey(key);

        Runnable parseTask = () -> {
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            assertEquals("baeldung", claims.getSubject());
        };

        Thread thread1 = new Thread(parseTask);
        Thread thread2 = new Thread(parseTask);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Assertions.fail("Thread execution was interrupted");
        }
    }

    @Test
    void givenDeprecatedParser_whenRequiringSpecificClaim_thenShouldParseSuccessfully() {
        JwtParser parser = Jwts.parser()
            .setSigningKey(key);

        Claims claims = parser.parseClaimsJws(token)
            .getBody();

        Assertions.assertEquals("baeldung", claims.getSubject());
    }

    @Test
    public void givenDeprecatedParser_whenRequiringNonExistentClaim_thenShouldFail() {

        JwtParser parser = Jwts.parser()
            .setSigningKey(key);

        try {
            Claims claims = parser.parseClaimsJws(token)
                .getBody();
            Assertions.assertEquals(null, claims.get("non-existent-claim"));
        } catch (Exception e) {
            Assertions.assertEquals("JWT claims string is empty.", e.getMessage());
        }
    }

}
