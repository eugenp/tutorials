package org.baeldung.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user1")
        .password("123456")
        .roles("USER")
        .and()
        .withUser("admin")
        .password("123456")
        .roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .csrf()
        .disable()
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/admin")
        .hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        
        // login
        .and()
        .formLogin()

        // logout
        .and()
        .logout()
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/login")
        .and()
        .exceptionHandling()
        //.accessDeniedPage("/403");
        .accessDeniedHandler(accessDeniedHandler());
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler("/403");
    }
}
