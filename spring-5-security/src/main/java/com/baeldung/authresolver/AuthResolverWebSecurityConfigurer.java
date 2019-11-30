package com.baeldung.authresolver;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AuthResolverWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    public AuthenticationConverter authenticationConverter() {
        return new BasicAuthenticationConverter();
    }

    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver() {
        return request -> {
            if (request
              .getPathInfo()
              .startsWith("/employee")) return employeesAuthenticationManager();
            return customersAuthenticationManager();
        };
    }

    public AuthenticationManager customersAuthenticationManager() {
        return authentication -> {
            if (isCustomer(authentication)) {
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            }
            throw new UsernameNotFoundException(authentication
              .getPrincipal()
              .toString());
        };
    }

    public boolean isCustomer(Authentication authentication) {
        return (authentication
          .getPrincipal()
          .toString()
          .startsWith("customer"));
    }

    public boolean isEmployee(Authentication authentication) {
        return (authentication
          .getPrincipal()
          .toString()
          .startsWith("employee"));
    }

    public AuthenticationFilter authenticationFilter(AuthenticationManagerResolver<HttpServletRequest> resolver, AuthenticationConverter converter) {
        AuthenticationFilter ret = new AuthenticationFilter(resolver, converter);
        ret.setSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
        });
        return ret;
    }

    public AuthenticationManager employeesAuthenticationManager() {
        return authentication -> {
            if (isEmployee(authentication)) {
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            }
            throw new UsernameNotFoundException(authentication
              .getPrincipal()
              .toString());
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .addFilterBefore(
          authenticationFilter(
            authenticationManagerResolver(), authenticationConverter()),
          BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
