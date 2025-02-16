package com.baeldung.securityconfig.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests((authz) -> authz
            .requestMatchers("/").hasRole("USER")
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
          .formLogin(withDefaults())
          .httpBasic(withDefaults())
          .logout(logout -> logout
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .logoutSuccessHandler(webSecurityUserLogoutHandler())
            .deleteCookies("JSESSIONID"));
        return http.build();
    }

    @Bean
    public LogoutSuccessHandler webSecurityUserLogoutHandler() {
        return (request, response, authentication) -> {
          System.out.println("User logged out successfully!");
          response.sendRedirect("/app");
        };
    }

    @Bean
    @Profile("inmemory")
    public AuthenticationManager inMemoryAuthManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
          http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
          .inMemoryAuthentication()
            .withUser("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .and()
            .withUser("admin")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN");
        return authenticationManagerBuilder.build();
    }

    @Bean
    @Profile("jdbc")
    public AuthenticationManager jdbcAuthManager(HttpSecurity http, DataSource dataSource,
      PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
          http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
          .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder)
            .withDefaultSchema()
          .withUser("user")
          .password(passwordEncoder.encode("password"))
          .roles("USER")
          .and()
          .withUser("admin")
          .password(passwordEncoder.encode("password"))
          .roles("ADMIN");

        return authenticationManagerBuilder.build();
    }

    @Bean
    @Profile("jdbc")
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}