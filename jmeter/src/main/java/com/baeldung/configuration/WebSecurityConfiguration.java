package com.baeldung.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Set<UserDetails> users = new HashSet<>();
        users.add(User.withUsername("admin").password(encoder.encode("admin")).roles("USER", "ADMIN").build());
        for(int i=1;i<=10;i++){
            users.add(User.withUsername("user"+i).password(encoder.encode("password")+i).roles("USER").build());
        }

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        http
          .authorizeRequests()
          .antMatchers("/secured/**").authenticated()        
          .anyRequest().permitAll()
          .and()
          .httpBasic();

        return http.build();
    }    
}
