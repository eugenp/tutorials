package org.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http) {
        return http.authorizeExchange()
          // We'll open up all actuator endpoints for demo purposes
          .pathMatchers("/actuator/**").permitAll()
          .anyExchange().authenticated()
          .and().build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
          .username("user").password("password").roles("USER")
          .build();
        return new MapReactiveUserDetailsService(user);
    }

}
