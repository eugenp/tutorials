package com.baeldung.loginredirect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
class LoginRedirectSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(encoder().encode("user"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(new LoginPageFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/loginUser")
            .permitAll()
            .antMatchers("/user*")
            .hasRole("USER")
            .and()
            .formLogin()
            .loginPage("/loginUser")
            .loginProcessingUrl("/user_login")
            .failureUrl("/loginUser?error=loginError")
            .defaultSuccessUrl("/userMainPage")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/user_logout")
            .logoutSuccessUrl("/loginUser")
            .deleteCookies("JSESSIONID")
            .and()
            .csrf()
            .disable();
        return http.build();
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
