package org.baeldung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
            http
                .anonymous().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/home.html","/post","/postSchedule","/posts").hasRole("USER")
                .and()
                .httpBasic().authenticationEntryPoint(oauth2AuthenticationEntryPoint())
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        // @formatter:on
    }

    private LoginUrlAuthenticationEntryPoint oauth2AuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/login");
    }

}
