package com.baeldung.httpsecurityvswebsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Given: HttpSecurity configured

        http.authorizeRequests()
          .requestMatchers("/public/**").permitAll()
          .requestMatchers("/admin/**").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and()
          .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(s-> s.permitAll());


        // When: Accessing specific URLs
        // Then: Access is granted based on defined rules
        return http.build();
    }
}
