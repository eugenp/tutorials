package org.baeldung.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring Security Configuration.
 */
@Configuration
@EnableWebSecurity
@ImportResource({ "classpath:webSecurityConfig.xml" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler;

    public SecurityConfig() {
        super();
    }

}
