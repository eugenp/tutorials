package com.baeldung.spring.cloud.bootstrap.gateway;

import com.baeldung.spring.cloud.bootstrap.gateway.filter.CsrfHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("password").roles("USER")
                .and()
            .withUser("admin").password("admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .loginPage("/login.html")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/home/index.html")
            .and()
        .authorizeRequests()
            .antMatchers("/book-service/**", "/rating-service/**", "/login*").permitAll()
            .antMatchers("/eureka/**").hasRole("ADMIN")
            .antMatchers("/home/*").authenticated()
            .anyRequest().authenticated()
            .and()
        .logout()
            .and()
        .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
        .csrf().csrfTokenRepository(csrfTokenRepository());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
