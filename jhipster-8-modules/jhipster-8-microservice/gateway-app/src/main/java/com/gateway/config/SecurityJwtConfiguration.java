package com.gateway.config;

import static com.gateway.security.SecurityUtils.AUTHORITIES_KEY;
import static com.gateway.security.SecurityUtils.JWT_ALGORITHM;

import com.gateway.management.SecurityMetersService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;

@Configuration
public class SecurityJwtConfiguration {

    private final Logger log = LoggerFactory.getLogger(SecurityJwtConfiguration.class);

    @Value("${jhipster.security.authentication.jwt.base64-secret}")
    private String jwtKey;

    @Bean
    public ReactiveJwtDecoder jwtDecoder(SecurityMetersService metersService) {
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        return token -> {
            try {
                return jwtDecoder
                    .decode(token)
                    .doOnError(e -> {
                        if (e.getMessage().contains("Jwt expired at")) {
                            metersService.trackTokenExpired();
                        } else if (e.getMessage().contains("Failed to validate the token")) {
                            metersService.trackTokenInvalidSignature();
                        } else if (
                            e.getMessage().contains("Invalid JWT serialization:") ||
                            e.getMessage().contains("Invalid unsecured/JWS/JWE header:")
                        ) {
                            metersService.trackTokenMalformed();
                        } else {
                            log.error("Unknown JWT reactive error {}", e.getMessage());
                        }
                    });
            } catch (Exception e) {
                if (e.getMessage().contains("An error occurred while attempting to decode the Jwt")) {
                    metersService.trackTokenMalformed();
                } else if (e.getMessage().contains("Failed to validate the token")) {
                    metersService.trackTokenInvalidSignature();
                } else {
                    log.error("Unknown JWT error {}", e.getMessage());
                }
                throw e;
            }
        };
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_KEY);

        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
            new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter)
        );
        return jwtAuthenticationConverter;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }
}
