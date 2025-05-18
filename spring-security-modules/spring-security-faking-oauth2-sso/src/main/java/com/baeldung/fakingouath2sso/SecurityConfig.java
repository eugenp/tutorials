package com.baeldung.fakingouath2sso;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                a -> a.requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/oauth2/**"), new AntPathRequestMatcher("/openid-connect"),
                        new AntPathRequestMatcher("/error"), new AntPathRequestMatcher("/css/**"), new AntPathRequestMatcher("/js/**"),
                        new AntPathRequestMatcher("/images/**"), new AntPathRequestMatcher("/assets/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
            .oauth2Login(customizer -> customizer.successHandler(successHandler()))
            .build();
    }

    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/");
        return handler;
    }

}
