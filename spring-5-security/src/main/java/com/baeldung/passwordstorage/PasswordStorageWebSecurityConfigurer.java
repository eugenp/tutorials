package com.baeldung.passwordstorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordStorageWebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false) // 4
          .userDetailsService(getUserDefaultDetailsService())
          .passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService getUserDefaultDetailsService() {
        User testUser = new User("baeldung", "{noop}SpringSecurity5", Collections.emptyList());
        return new InMemoryUserDetailsManager(testUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // set up the list of supported encoders and their prefixes
        PasswordEncoder defaultEncoder = new StandardPasswordEncoder();
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

        return passwordEncoder;
    }

}
