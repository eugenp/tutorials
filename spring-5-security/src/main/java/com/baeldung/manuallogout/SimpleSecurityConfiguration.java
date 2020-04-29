package com.baeldung.manuallogout;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import javax.servlet.http.Cookie;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
public class SimpleSecurityConfiguration {

    @Order(3)
    @Configuration
    public static class DefaultLogoutConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/basic/**")
                    .authorizeRequests(authz -> authz.anyRequest().permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/basic/basiclogout")
                            .addLogoutHandler(new SecurityContextLogoutHandler())
                            .addLogoutHandler(new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY))
                    );
        }
    }

    @Order(2)
    @Configuration
    public static class AllCookieClearingLogoutConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/cookies/**")
                    .authorizeRequests(authz -> authz.anyRequest().permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/cookies/cookielogout")
                            .addLogoutHandler(new SecurityContextLogoutHandler())
                            .addLogoutHandler((request, response, auth) -> {
                                        for (Cookie cookie : request.getCookies()) {
                                            String cookieName = cookie.getName();
                                            Cookie cookieToDelete = new Cookie(cookieName, null);
                                            cookieToDelete.setMaxAge(0);
                                            response.addCookie(cookieToDelete);
                                        }
                                    }
                            ));
        }
    }

    @Order(1)
    @Configuration
    public static class ClearSiteDataHeaderLogoutConfiguration extends WebSecurityConfigurerAdapter {

        private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
                { CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS };

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/csd/**")
                    .authorizeRequests(authz -> authz.anyRequest().permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/csd/csdlogout")
                            .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
                    );
        }
    }
}
