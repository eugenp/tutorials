package com.baeldung.contentsecuritypolicy;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class ContentSecurityPolicySecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"https://localhost:8080/report\"}]}";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
          .disable()
          .authorizeRequests()
          .antMatchers("/**")
          .permitAll()
          .and()
          .headers()
          .addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO))
          .xssProtection()
          .and()
          .contentSecurityPolicy("form-action 'self'; report-uri /report; report-to csp-violation-report");
    }
}
