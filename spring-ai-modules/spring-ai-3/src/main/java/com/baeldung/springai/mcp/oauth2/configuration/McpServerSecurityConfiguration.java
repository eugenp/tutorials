package com.baeldung.springai.mcp.oauth2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class McpServerSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
          .authorizeHttpRequests(auth -> auth
            .requestMatchers("/mcp/**").authenticated()
            .requestMatchers("/sse").authenticated()
            .anyRequest().permitAll())
          .with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults())
          .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
          .csrf(CsrfConfigurer::disable)
          .cors(Customizer.withDefaults())
          .build();
    }
}
