package com.baeldung.multiplelogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class MultipleLoginSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
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
        public SecurityFilterChain filterChainApp1(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/admin*")
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin*")).hasRole("ADMIN"))
                // log in
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/loginAdmin")
                                .loginProcessingUrl("/admin_login")
                                .failureUrl("/loginAdmin?error=loginError")
                                .defaultSuccessUrl("/adminPage"))
                // logout
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/admin_logout")
                                .logoutSuccessUrl("/protectedLinks")
                                .deleteCookies("JSESSIONID"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/403"))
                .csrf(AbstractHttpConfigurer::disable);

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
        public SecurityFilterChain filterChainApp2(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/user*")
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/user*")).hasRole("USER"))
                // log in
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/loginUser")
                                .loginProcessingUrl("/user_login")
                                .failureUrl("/loginUser?error=loginError")
                                .defaultSuccessUrl("/userPage"))
                // logout
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/user_logout")
                                .logoutSuccessUrl("/protectedLinks")
                                .deleteCookies("JSESSIONID"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/403"))
                .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }

}
