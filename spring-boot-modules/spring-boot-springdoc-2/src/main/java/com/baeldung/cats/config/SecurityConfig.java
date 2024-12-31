package com.baeldung.cats.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String TEST_TOKEN = "valid-token";
    private static final String ACCESS_DENIED = "access denied";

    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/swagger-ui/index.html", "/api-docs/**")
            .permitAll()
            .anyRequest()
            .authenticated())
            .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.decoder(jwtDecoder())))
            .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return token -> {
            if (!TEST_TOKEN.equals(token)) {
                log.warn(ACCESS_DENIED);
                throw new JwtException(ACCESS_DENIED);
            }

            return Jwt.withTokenValue(token)
                .claim("token", TEST_TOKEN)
                .header("token", TEST_TOKEN)
                .build();
        };
    }
}
