package org.baeldung.spring;

import org.baeldung.security.MySavedRequestAwareAuthenticationSuccessHandler;
import org.baeldung.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("org.baeldung.security")
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityJavaConfig() {
        super();
    }

    //

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("temporary").password("temporary").roles("ADMIN").and().withUser("user").password("userPass").roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception { // @formatter:off
        http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers("/api/foos").authenticated()
        .and()
        .formLogin()
        .loginProcessingUrl("/j_spring_security_check")
        .usernameParameter("j_username")
        .passwordParameter("j_password")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
        .and()
        .logout();
    } // @formatter:on

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

}