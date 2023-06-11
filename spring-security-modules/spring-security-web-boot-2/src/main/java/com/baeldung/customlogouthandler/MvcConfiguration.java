package com.baeldung.customlogouthandler;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.baeldung.customlogouthandler.web.CustomLogoutHandler;

@Configuration
@EnableWebSecurity
public class MvcConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/user/**")
            .hasRole("USER")
            .and()
            .logout()
            .logoutUrl("/user/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
            .permitAll()
            .and()
            .csrf()
            .disable()
            .formLogin()
            .disable();
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select login, password, true from users where login=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select login, role from users where login=?");
        return jdbcUserDetailsManager;
    }
}
