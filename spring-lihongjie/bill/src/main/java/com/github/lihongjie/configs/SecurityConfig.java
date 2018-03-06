package com.github.lihongjie.configs;

import com.github.lihongjie.service.impl.AuthorizedUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by lihongjie on 7/23/17.
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(authorizedUserDetailsService())
                .passwordEncoder(new StandardPasswordEncoder());
    }

    /**
     * 配置定制的login page
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")
                    .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/access_denied");
    }

    @Bean
    public AuthorizedUserDetailsService authorizedUserDetailsService() {
        return new AuthorizedUserDetailsService();
    }
}
