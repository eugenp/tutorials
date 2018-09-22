package org.baeldung.voter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter: off
        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("pass")).roles("USER").and().withUser("admin").password(passwordEncoder().encode("pass")).roles("ADMIN");
        // @formatter: on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter: off
        http
                // needed so our login could work
                .csrf().disable().authorizeRequests().anyRequest().authenticated().accessDecisionManager(accessDecisionManager()).antMatchers("/").hasAnyRole("ROLE_ADMIN", "ROLE_USER").and().formLogin().permitAll().and().logout().permitAll()
                .deleteCookies("JSESSIONID").logoutSuccessUrl("/login");
        // @formatter: on
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        // @formatter: off
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(), new RoleVoter(), new AuthenticatedVoter(), new MinuteBasedVoter());
        // @formatter: on
        return new UnanimousBased(decisionVoters);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
