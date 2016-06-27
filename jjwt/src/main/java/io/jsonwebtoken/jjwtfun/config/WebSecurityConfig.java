package io.jsonwebtoken.jjwtfun.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("#{ @environment['jjwtfun.secret'] ?: 'secret' }")
    String secret;

    @Autowired
    CsrfTokenRepository jwtCsrfTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(new JwtCsrfValidatorFilter(), CsrfFilter.class)
            .csrf()
                .csrfTokenRepository(jwtCsrfTokenRepository)
                .ignoringAntMatchers("/dynamic-builder-general")
                .ignoringAntMatchers("/dynamic-builder-specific")
            .and().authorizeRequests()
                .antMatchers("/**")
                .permitAll();
    }

    private class JwtCsrfValidatorFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // NOTE: A real implementation should have a nonce cache so the token cannot be reused

            CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

            // CsrfFilter already made sure the token matched.
            // Here, we'll make sure it's not expired
            if ("POST".equals(request.getMethod()) && token != null) {
                try {
                    Jwts.parser()
                        .setSigningKey(secret.getBytes("UTF-8"))
                        .parseClaimsJws(token.getToken());
                } catch (JwtException e) {
                    // most likely an ExpiredJwtException, but this will handle any
                    request.setAttribute("exception", e);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("expired-jwt");
                    dispatcher.forward(request, response);
                }
            }

            filterChain.doFilter(request, response);
        }
    }
}
