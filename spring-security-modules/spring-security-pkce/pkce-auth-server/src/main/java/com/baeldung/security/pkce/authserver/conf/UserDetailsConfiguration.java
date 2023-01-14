package com.baeldung.security.pkce.authserver.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfiguration {
    
    @Bean 
    public UserDetailsService userDetailsService() {
        // @formatter:off
        UserDetails userDetails = User.withDefaultPasswordEncoder()
          .username("user")
          .password("password")
          .roles("USER")
          .build();

        return new InMemoryUserDetailsManager(userDetails);        
        // @formatter:on
    }

}
