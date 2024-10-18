package com.baeldung.roles.voter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
        .withUser("user")
        .password(passwordEncoder().encode("pass"))
        .roles("USER")
        .and()
        .withUser("admin")
        .password(passwordEncoder().encode("pass"))
        .roles("ADMIN");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated()
                            .anyRequest().access(accessDecisionManager()))
            .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
            .logout(httpSecurityLogoutConfigurer ->
                    httpSecurityLogoutConfigurer.permitAll().deleteCookies("JSESSIONID")
                            .logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> accessDecisionManager() {
        return AuthorizationManagers.allOf(new MinuteBasedVoter());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
