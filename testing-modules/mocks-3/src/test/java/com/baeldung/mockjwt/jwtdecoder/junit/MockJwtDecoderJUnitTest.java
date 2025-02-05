package com.baeldung.mockjwt.jwtdecoder.junit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Instant;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MockJwtDecoderJUnitTest {

    @Autowired
    private MockMvc mockMvc;

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

        List<GrantedAuthority> authorities = ((List<String>) jwt.getClaim("roles"))
            .stream()
            .map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(
            jwt,
            authorities,
            jwt.getClaim("sub")
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<String> response = userController.getUserInfo(jwt);

        assertEquals("Hello, john.doe", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void whenInvalidToken_thenThrowsException() throws Exception {
        when(jwtDecoder.decode("invalid_token"))
            .thenThrow(new JwtValidationException(
                "Invalid token",
                Arrays.asList(new OAuth2Error("invalid_token"))
            ));

        // Simulate token validation (this would normally happen in the filter chain)
        assertThrows(JwtValidationException.class, () -> jwtDecoder.decode("invalid_token"));
    }

    @Test
    void whenTokenExpired_thenThrowsException() {
        when(jwtDecoder.decode("expired_token"))
            .thenThrow(new JwtValidationException(
                "Token expired",
                Arrays.asList(new OAuth2Error("expired_token"))
            ));

        JwtValidationException thrown = assertThrows(
            JwtValidationException.class,
            () -> jwtDecoder.decode("expired_token")
        );

        assertEquals("Token expired", thrown.getMessage());
    }
}