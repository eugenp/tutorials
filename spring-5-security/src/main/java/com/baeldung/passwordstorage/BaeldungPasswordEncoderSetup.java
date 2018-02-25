package com.baeldung.passwordstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BaeldungPasswordEncoderSetup {

    private final static Logger LOG = LoggerFactory.getLogger(BaeldungPasswordEncoderSetup.class);

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessListener(final PasswordEncoder encoder) {

        return (AuthenticationSuccessEvent event) -> {
            final Authentication auth = event.getAuthentication();

            if (auth instanceof UsernamePasswordAuthenticationToken && auth.getCredentials() != null) {

                final CharSequence clearTextPass = (CharSequence) auth.getCredentials(); // 1
                final String newPasswordHash = encoder.encode(clearTextPass); // 2

                LOG.info("New password hash {} for user {}", newPasswordHash, auth.getName());

                ((UsernamePasswordAuthenticationToken) auth).eraseCredentials(); // 3
            }
        };
    }
}
