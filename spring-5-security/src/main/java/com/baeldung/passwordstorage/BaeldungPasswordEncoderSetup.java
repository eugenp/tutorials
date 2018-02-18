package com.baeldung.passwordstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BaeldungPasswordEncoderSetup {

    private final static Logger LOG = LoggerFactory.getLogger(BaeldungPasswordEncoderSetup.class);

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(final ApplicationEventPublisher publisher) {
        return new DefaultAuthenticationEventPublisher(publisher);
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessListener(final PasswordEncoder encoder) {
        return (AuthenticationSuccessEvent event) -> {
            final Authentication authentication = event.getAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken && authentication.getCredentials() != null) {
                final CharSequence clearTextPassword = (CharSequence) authentication.getCredentials(); // 1
                final String newPasswordHash = encoder.encode(clearTextPassword); // 2

                LOG.info("New password hash {} for user {}", newPasswordHash, authentication.getName());

                ((UsernamePasswordAuthenticationToken) authentication).eraseCredentials(); // 3
            }
        };
    }
}
