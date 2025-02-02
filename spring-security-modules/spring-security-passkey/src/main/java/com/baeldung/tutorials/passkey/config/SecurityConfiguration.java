package com.baeldung.tutorials.passkey.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableConfigurationProperties(SecurityConfiguration.WebAuthNProperties.class)
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain webauthnFilterChain(HttpSecurity http, WebAuthNProperties webAuthNProperties) throws Exception {
        return http.authorizeHttpRequests( ht -> ht.anyRequest().authenticated())
          .formLogin(Customizer.withDefaults())
          .webAuthn( webauth ->
              webauth.allowedOrigins(webAuthNProperties.getAllowedOrigins())
                .rpId(webAuthNProperties.getRpId())
                .rpName(webAuthNProperties.getRpName())
          )
          .build();
    }

    @ConfigurationProperties(prefix = "spring.security.webauthn")
    @Data
    static class WebAuthNProperties {
        private String rpId;
        private String rpName;
        private Set<String> allowedOrigins;
    }

}
