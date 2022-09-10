package com.baeldung.cassecuredapp.config;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.Collections;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    private SingleSignOutFilter singleSignOutFilter;
    private LogoutFilter logoutFilter;
    private CasAuthenticationProvider casAuthenticationProvider;
    private ServiceProperties serviceProperties;

    @Autowired
    public WebSecurityConfig(SingleSignOutFilter singleSignOutFilter, LogoutFilter logoutFilter,
                             CasAuthenticationProvider casAuthenticationProvider,
                             ServiceProperties serviceProperties) {
        this.logoutFilter = logoutFilter;
        this.singleSignOutFilter = singleSignOutFilter;
        this.serviceProperties = serviceProperties;
        this.casAuthenticationProvider = casAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers( "/secured", "/login").authenticated()
          .and()
          .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
          .and()
          .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class)
          .addFilterBefore(logoutFilter, LogoutFilter.class)
          .csrf().ignoringAntMatchers("/exit/cas");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(casAuthenticationProvider);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(casAuthenticationProvider));
    }

    public AuthenticationEntryPoint authenticationEntryPoint() {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl("https://localhost:8443/login");
        entryPoint.setServiceProperties(serviceProperties);
        return entryPoint;
    }



}
