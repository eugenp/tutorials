package com.baeldung.manuallogout;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.CACHE;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.EXECUTION_CONTEXTS;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.STORAGE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class SimpleSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSecurityConfiguration.class);

    @Order(4)
    @Configuration
    public static class LogoutOnRequestConfiguration {

        @Bean
        public SecurityFilterChain filterChainLogoutOnRequest(HttpSecurity http) throws Exception {
            http.securityMatcher("/request/**")
                .authorizeHttpRequests(authz -> authz.anyRequest()
                    .permitAll())
                .logout(logout -> logout.logoutUrl("/request/logout")
                    .addLogoutHandler((request, response, auth) -> {
                        try {
                            request.logout();
                        } catch (ServletException e) {
                            logger.error(e.getMessage());
                        }
                    }));
            return http.build();
        }
    }

    @Order(3)
    @Configuration
    public static class DefaultLogoutConfiguration {
        
        @Bean
        public SecurityFilterChain filterChainDefaultLogout(HttpSecurity http) throws Exception {
            http.securityMatcher("/basic/**")
                .authorizeHttpRequests(authz -> authz.anyRequest()
                .permitAll())
            .logout(logout -> logout.logoutUrl("/basic/basiclogout"));
            return http.build();
        }
    }

    @Order(2)
    @Configuration
    public static class AllCookieClearingLogoutConfiguration {

        @Bean
        public SecurityFilterChain filterChainAllCookieClearing(HttpSecurity http) throws Exception {
            http.securityMatcher("/cookies/**")
                .authorizeHttpRequests(authz -> authz.anyRequest()
                    .permitAll())
                .logout(logout -> logout.logoutUrl("/cookies/cookielogout")
                    .addLogoutHandler(new SecurityContextLogoutHandler())
                    .addLogoutHandler((request, response, auth) -> {
                        for (Cookie cookie : request.getCookies()) {
                            String cookieName = cookie.getName();
                            Cookie cookieToDelete = new Cookie(cookieName, null);
                            cookieToDelete.setMaxAge(0);
                            response.addCookie(cookieToDelete);
                        }
                    }));
            return http.build();
        }
    }

    @Order(1)
    @Configuration
    public static class ClearSiteDataHeaderLogoutConfiguration {

        private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = { CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS };

        @Bean
        public SecurityFilterChain filterChainClearSiteDataHeader(HttpSecurity http) throws Exception {
            http.securityMatcher("/csd/**")
                .authorizeHttpRequests(authz -> authz.anyRequest()
                    .permitAll())
                .logout(logout -> logout.logoutUrl("/csd/csdlogout")
                    .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE))));
            return http.build();
        }
    }
}
