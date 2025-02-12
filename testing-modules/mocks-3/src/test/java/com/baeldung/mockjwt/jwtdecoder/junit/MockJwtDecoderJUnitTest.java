package com.baeldung.mockjwt.jwtdecoder.junit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        List<GrantedAuthority> authorities = ((List<String>) jwt.getClaim("roles")).stream()
            .map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("sub"));
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        ResponseEntity<String> response = userController.getUserInfo(jwt);

        assertEquals("Hello, john.doe", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(authentication.getAuthorities()
            .stream()
            .anyMatch(auth -> auth.getAuthority()
                .equals("ROLE_ADMIN")));
    }

    @Test
    void whenInvalidToken_thenThrowsException() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", null);

        Jwt invalidJwt = Jwt.withTokenValue("invalid_token")
            .header("alg", "none")
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(invalidJwt);
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        JwtValidationException exception = assertThrows(JwtValidationException.class, () -> {
            userController.getUserInfo(invalidJwt);
        });

        assertEquals("Invalid token", exception.getMessage());
    }

    @Test
    void whenExpiredToken_thenThrowsException() throws Exception {
        // Simulate an expired JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "john.doe");
        claims.put("exp", Instant.now().minus(1, ChronoUnit.DAYS));

        Jwt expiredJwt = Jwt.withTokenValue("expired_token")
            .header("alg", "none")
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(expiredJwt);
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        JwtValidationException exception = assertThrows(JwtValidationException.class, () -> {
            userController.getUserInfo(expiredJwt);
        });

        assertEquals("Token has expired", exception.getMessage());
    }
}