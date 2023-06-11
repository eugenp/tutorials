package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Profile("!https")
public class SecSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() throws Exception {
        UserDetails user1 = User.withUsername("user1")
            .password("{noop}user1Pass")
            .roles("USER")
            .build();

        UserDetails user2 = User.withUsername("user2")
            .password("{noop}user2Pass")
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername("admin")
            .password("{noop}admin0Pass")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**")
            .hasRole("ADMIN")
            .antMatchers("/anonymous*")
            .anonymous()
            .antMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico", "/rest")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/index.html")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/homepage.html", true)
            .failureUrl("/index.html?error=true")
            .and()
            .logout()
            .logoutUrl("/perform_logout")
            .deleteCookies("JSESSIONID");
        return http.build();
    }
}
