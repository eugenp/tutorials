package com.baeldung.httpfirewall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.Arrays;

@Configuration
public class HttpFirewallConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
          .csrf()
          .disable()
          .authorizeRequests()
          .antMatchers("/error")
          .permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic();
        //@formatter:on
    }

    @Bean
    public HttpFirewall configureFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        strictHttpFirewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS")); // Allow only HTTP GET, POST, DELETE and OPTIONS methods
        return strictHttpFirewall;
    }

    /*
     Use this bean if you are using Spring Security 5.4 and above
     */
    @Bean
    public RequestRejectedHandler requestRejectedHandler() {
        return new HttpStatusRequestRejectedHandler(); // Default status code is 400. Can be customized
    }

}
