package com.baeldung.mockjwt.jwtdecoder.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@ExtendWith(MockitoExtension.class)
public class MockJwtDecoderJUnitTest {

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenValidToken_thenReturnsUserInfo() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "john.doe");

        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        ResponseEntity<String> response = userController.getUserInfo(jwt);

        assertEquals("Hello, john.doe", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenTokenHasCustomClaims_thenProcessesCorrectly() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "john.doe");
        claims.put("roles", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        claims.put("email", "john.doe@example.com");

        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        ResponseEntity<String> response = userController.getUserInfo(jwt);

        assertEquals("Hello, john.doe", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenInvalidToken_thenThrowsException() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "invalid.user");

        Jwt invalidJwt = Jwt.withTokenValue("invalid_token")
            .header("alg", "none")
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        when(jwtDecoder.decode("invalid_token")).thenThrow(new JwtValidationException("Invalid token", Arrays.asList(new OAuth2Error("invalid_token"))));

        JwtValidationException thrown = assertThrows(JwtValidationException.class, () -> jwtDecoder.decode("invalid_token"));

        assertEquals("Invalid token", thrown.getMessage());
    }

    @Test
    void whenTokenExpired_thenThrowsException() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "expired.user");
        claims.put("exp", Instant.now()
            .minusSeconds(3600));
        claims.put("iat", Instant.now()
            .minusSeconds(7200));

        Jwt expiredJwt = Jwt.withTokenValue("expired_token")
            .header("alg", "none")
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        when(jwtDecoder.decode("expired_token")).thenThrow(new JwtValidationException("Token expired", Arrays.asList(new OAuth2Error("invalid_token"))));

        JwtValidationException thrown = assertThrows(JwtValidationException.class, () -> jwtDecoder.decode("expired_token"));

        assertEquals("Token expired", thrown.getMessage());
    }
}