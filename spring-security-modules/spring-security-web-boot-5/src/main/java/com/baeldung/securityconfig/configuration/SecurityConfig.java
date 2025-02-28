package com.baeldung.securityconfig.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, LogoutSuccessHandler webSecurityUserLogoutHandler)
        throws Exception {
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
            .logoutSuccessHandler(webSecurityUserLogoutHandler)
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
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails user = User.builder()
          .username("user")
          .password(passwordEncoder().encode("password"))
          .roles("USER")
          .build();
        UserDetails admin = User.builder()
          .username("admin")
          .password(passwordEncoder().encode("password"))
          .roles("ADMIN")
          .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    @Profile("jdbc")
    public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        UserDetails user = User.builder()
          .username("user")
          .password(passwordEncoder().encode("password"))
          .roles("USER")
          .build();
        UserDetails admin = User.builder()
          .username("admin")
          .password(passwordEncoder().encode("password"))
          .roles("ADMIN")
          .build();
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    @Profile("jdbc")
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
          .setType(H2)
          .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
          .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}