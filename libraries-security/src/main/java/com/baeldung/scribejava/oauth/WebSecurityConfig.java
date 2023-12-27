package com.baeldung.scribejava.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableResourceServer
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http.headers( it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("baeldung")
            .password("scribejava")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @EnableResourceServer
    @Configuration
    public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/user/me")
                        .authenticated();
                }
            );
            http.csrf(AbstractHttpConfigurer::disable);
        }
    }

}
