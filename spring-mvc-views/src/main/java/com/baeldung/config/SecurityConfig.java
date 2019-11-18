package com.baeldung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutUrl("/logout.do")
                .invalidateHttpSession(true)
                .clearAuthentication(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/themes/**");
    }

}
