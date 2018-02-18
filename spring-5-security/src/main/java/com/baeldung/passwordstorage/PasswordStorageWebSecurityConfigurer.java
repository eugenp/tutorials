package com.baeldung.passwordstorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordStorageWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final AuthenticationEventPublisher eventPublisher;
    private final UserDetailsService userDetailsService;

    @Autowired
    public PasswordStorageWebSecurityConfigurer(AuthenticationEventPublisher eventPublisher, UserDetailsService userDetailsService) {
        this.eventPublisher = eventPublisher;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false) // 4
          .authenticationEventPublisher(eventPublisher)
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // set up the list of supported encoders and their prefixes
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));

        // get an instance of the DelegatingPasswordEncoder, set up to use our instance as default encoder
        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);

        // configure our instance as default encoder for actual matching
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoders.get(encodingId));

        return delegatingPasswordEncoder;
    }

}
