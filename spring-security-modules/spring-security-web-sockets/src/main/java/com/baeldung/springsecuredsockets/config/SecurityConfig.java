package com.baeldung.springsecuredsockets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.baeldung.springsecuredsockets.security.CustomAccessDeniedHandler;
import com.baeldung.springsecuredsockets.security.CustomDaoAuthenticationProvider;
import com.baeldung.springsecuredsockets.security.CustomLoginSuccessHandler;
import com.baeldung.springsecuredsockets.security.CustomLogoutSuccessHandler;
import com.baeldung.springsecuredsockets.security.CustomUserDetailsService;

/**
 * @EnableGlobalAuthentication annotates:
 * @EnableWebSecurity
 * @EnableWebMvcSecurity
 * @EnableGlobalMethodSecurity Passing in 'prePostEnabled = true' allows:
 * <p>
 * Pre/Post annotations such as:
 * @PreAuthorize("hasRole('ROLE_USER')")
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@ComponentScan("com.baeldung.springsecuredsockets")
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Login, Logout, Success, and Access Denied beans/handlers
     */

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    /**
     * Authentication beans
     */

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider bean = new CustomDaoAuthenticationProvider();
        bean.setUserDetailsService(customUserDetailsService);
        bean.setPasswordEncoder(encoder());
        return bean;
    }

    /**
     * Order of precedence is very important.
     * <p>
     * Matching occurs from top to bottom - so, the topmost match succeeds first.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/index", "/authenticate")
            .permitAll()
            .antMatchers("/secured/**/**", "/secured/**/**/**", "/secured/socket", "/secured/success")
            .authenticated()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .usernameParameter("username")
            .passwordParameter("password")
            .loginProcessingUrl("/authenticate")
            .successHandler(loginSuccessHandler())
            .failureUrl("/denied")
            .permitAll()
            .and()
            .logout()
            .logoutSuccessHandler(logoutSuccessHandler())
            .and()
            /**
             * Applies to User Roles - not to login failures or unauthenticated access attempts.
             */
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            .authenticationProvider(authenticationProvider());

        /** Disabled for local testing */
        http.csrf()
            .disable();

        /** This is solely required to support H2 console viewing in Spring MVC with Spring Security */
        http.headers()
            .frameOptions()
            .disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider())
            .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .antMatchers("/resources/**");
    }

}
