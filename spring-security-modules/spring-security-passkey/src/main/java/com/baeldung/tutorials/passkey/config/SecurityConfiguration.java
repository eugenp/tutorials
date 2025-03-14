package com.baeldung.tutorials.passkey.config;

import com.baeldung.tutorials.passkey.repository.DbPublicKeyCredentialUserEntityRepository;
import com.baeldung.tutorials.passkey.repository.DbUserCredentialRepository;
import com.baeldung.tutorials.passkey.repository.PasskeyCredentialRepository;
import com.baeldung.tutorials.passkey.repository.PasskeyUserRepository;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableConfigurationProperties(SecurityConfiguration.WebAuthNProperties.class)
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain webauthnFilterChain(HttpSecurity http, WebAuthNProperties webAuthNProperties) throws Exception {
        return http.authorizeHttpRequests( ht -> ht.anyRequest().authenticated())
          .formLogin(withDefaults())
          .webAuthn( webauth ->
              webauth.allowedOrigins(webAuthNProperties.getAllowedOrigins())
                .rpId(webAuthNProperties.getRpId())
                .rpName(webAuthNProperties.getRpName())
          )
          .build();
    }

    @Bean
    PublicKeyCredentialUserEntityRepository userEntityRepository(PasskeyUserRepository userRepository) {
        return new DbPublicKeyCredentialUserEntityRepository(userRepository);
    }

    @Bean
    UserCredentialRepository userCredentialRepository(PasskeyUserRepository userRepository, PasskeyCredentialRepository credentialRepository) {
        return new DbUserCredentialRepository(credentialRepository,userRepository);
    }

    @ConfigurationProperties(prefix = "spring.security.webauthn")
    @Data
    static class WebAuthNProperties {
        private String rpId;
        private String rpName;
        private Set<String> allowedOrigins;
    }

}
