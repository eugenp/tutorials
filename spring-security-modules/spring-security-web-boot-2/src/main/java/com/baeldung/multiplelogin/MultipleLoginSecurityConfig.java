package com.baeldung.multiplelogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MultipleLoginSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(encoder().encode("userPass")).roles("USER").build());
        manager.createUser(User.withUsername("admin").password(encoder().encode("adminPass")).roles("ADMIN").build());
        return manager;
    }
    
    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter {

        @Bean
        public UserDetailsService userDetailsServiceApp1() {
            UserDetails user = User.withUsername("admin")
                .password(encoder().encode("admin"))
                .roles("ADMIN")
                .build();
            return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
            http.antMatcher("/admin*")
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                // log in
                .and()
                .formLogin()
                .loginPage("/loginAdmin")
                .loginProcessingUrl("/admin_login")
                .failureUrl("/loginAdmin?error=loginError")
                .defaultSuccessUrl("/adminPage")
                // logout
                .and()
                .logout()
                .logoutUrl("/admin_logout")
                .logoutSuccessUrl("/protectedLinks")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .csrf()
                .disable();

            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter {

        @Bean
        public UserDetailsService userDetailsServiceApp2() {
            UserDetails user = User.withUsername("user")
                .password(encoder().encode("user"))
                .roles("USER")
                .build();
            return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {
            http.antMatcher("/user*")
                .authorizeRequests()
                .anyRequest()
                .hasRole("USER")
                // log in
                .and()
                .formLogin()
                .loginPage("/loginUser")
                .loginProcessingUrl("/user_login")
                .failureUrl("/loginUser?error=loginError")
                .defaultSuccessUrl("/userPage")
                // logout
                .and()
                .logout()
                .logoutUrl("/user_logout")
                .logoutSuccessUrl("/protectedLinks")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .csrf()
                .disable();
            return http.build();
        }
    }

}
