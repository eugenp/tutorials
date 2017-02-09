package com.baeldung.config;

import com.baeldung.security.*;
import com.baeldung.web.filter.CsrfCookieGeneratorFilter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.inject.Inject;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject private JHipsterProperties jHipsterProperties;

    @Inject private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject private UserDetailsService userDetailsService;

    @Inject private RememberMeServices rememberMeServices;

    @Bean public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}").antMatchers("/bower_components/**").antMatchers("/i18n/**").antMatchers("/content/**").antMatchers("/swagger-ui/index.html").antMatchers("/test/**")
            .antMatchers("/h2-console/**");
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and().addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class).exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint).and().rememberMe()
            .rememberMeServices(rememberMeServices).rememberMeParameter("remember-me").key(jHipsterProperties.getSecurity().getRememberMe().getKey()).and().formLogin().loginProcessingUrl("/api/authentication").successHandler(ajaxAuthenticationSuccessHandler)
            .failureHandler(ajaxAuthenticationFailureHandler).usernameParameter("j_username").passwordParameter("j_password").permitAll().and().logout().logoutUrl("/api/logout").logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID", "CSRF-TOKEN").permitAll().and().headers().frameOptions().disable().and().authorizeRequests().antMatchers("/api/register").permitAll().antMatchers("/api/activate").permitAll().antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset_password/init").permitAll().antMatchers("/api/account/reset_password/finish").permitAll().antMatchers("/api/profile-info").permitAll().antMatchers("/api/**").authenticated().antMatchers("/management/**")
            .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/v2/api-docs/**").permitAll().antMatchers("/swagger-resources/configuration/ui").permitAll().antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);

    }

    @Bean public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
