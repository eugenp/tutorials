package com.baeldung.removingroleprefix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
public class UserDetailsConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails admin = User.withUsername("admin")
          .password(encoder.encode("password"))
          .authorities(Arrays.asList(new SimpleGrantedAuthority("ADMIN"),
             new SimpleGrantedAuthority("ADMINISTRATION")))
          .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
