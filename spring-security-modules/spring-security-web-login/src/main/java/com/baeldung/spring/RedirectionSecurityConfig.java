package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

//@Configuration
//@ImportResource({ "classpath:RedirectionWebSecurityConfig.xml" })
//@EnableWebSecurity
//@Profile("!https")
public class RedirectionSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
            .password("user1Pass")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user1);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login*")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .successHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        // .successHandler(new RefererAuthenticationSuccessHandler())
        return http.build();
    }

}
