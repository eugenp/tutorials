package com.baeldung.test.soap.keycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.authorizeRequests()
		 .antMatchers("/service/studentDetailsWsdl.wsdl")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .oauth2ResourceServer()
         .jwt();
	}


}