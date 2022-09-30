package com.baeldung.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * REST APIs that contains all the operations that can be performed for authentication like login, registration etc
 */
@RequestMapping("/api/auth")
@RestController
@Tag(name = "Authentication", description = "The Authentication API. Contains operations like change password, forgot password, login, logout, etc.")
public class AuthenticationApi {

    private final UserDetailsService userDetailsService;
    private final JwtEncoder encoder;

    public AuthenticationApi(UserDetailsService userDetailsService, JwtEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    /**
     * API to Login
     *
     * @param user The login entity that contains username and password
     * @return Returns the JWT token
     * @see com.baeldung.jwt.User
     */
    @Operation(summary = "User Authentication", description = "Authenticate the user and return a JWT token if the user is valid.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\n" + "  \"username\": \"jane\",\n"
      + "  \"password\": \"password\"\n" + "}", summary = "User Authentication Example")))
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (user.getPassword().equalsIgnoreCase(userDetails.getPassword())) {
            String token = generateToken(userDetails);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-AUTH-TOKEN", token);
            return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).body("{\"token\":\"" + token + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body("Invalid username or password");
        }
    }

    /**
     * Generates the  JWT token with claims
     *
     * @param userDetails The user details
     * @return Returns the JWT token
     */
    private String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();
        // @formatter:on
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
