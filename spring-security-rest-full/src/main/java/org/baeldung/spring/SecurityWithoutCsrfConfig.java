package org.baeldung.spring;

import org.baeldung.web.error.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
//
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @ImportResource({ "classpath:webSecurityConfig.xml" })
public class SecurityWithoutCsrfConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityWithoutCsrfConfig() {
        super();
    }

    // java config

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1").password("user1Pass").authorities("ROLE_USER").and().withUser("admin").password("adminPass").authorities("ROLE_ADMIN");
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/admin/*").hasRole("ADMIN")
        .antMatchers("/auth/*").hasAnyRole("ADMIN","USER")
        .antMatchers("/*").permitAll()
        .and()
        .httpBasic()
        .and()
        // .exceptionHandling().accessDeniedPage("/my-error-page")
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
        .and()
        .headers().cacheControl().disable()
        ;
        // @formatter:on
    }

}
