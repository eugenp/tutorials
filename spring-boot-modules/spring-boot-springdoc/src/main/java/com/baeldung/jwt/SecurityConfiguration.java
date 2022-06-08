package com.baeldung.jwt;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.security.core.userdetails.User;

/**
 * This class is inspired from
 * https://github.com/spring-projects/spring-security-samples/blob/5.7.x/servlet/spring-boot/java/jwt/login/src/main/java/example/RestConfig.java
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Value("${jwt.public.key}")
    RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    RSAPrivateKey privateKey;

    /**
     * This bean is used to configure the JWT token. Configure the URLs that should not be protected by the JWT token.
     *
     * @param http the HttpSecurity object
     * @return the HttpSecurity object
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //@formatter:off
        return http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests

                        .antMatchers("/api/auth/**", "/swagger-ui-custom.html" ,"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**",
                                "/swagger-ui/index.html","/api-docs/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                        .and())
                .build();
        //@formatter:on
    }

    /**
     * For demonstration/example, we use the InMemoryUserDetailsManager.
     *
     * @return Returns the UserDetailsService with pre-configure credentials.
     * @see InMemoryUserDetailsManager
     */
    @Bean
    UserDetailsService allUsers() {
        // @formatter:off
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager
                .createUser(User.builder()
                        .passwordEncoder(password -> password)
                        .username("john")
                        .password("password")
                        .authorities("USER")
                        .roles("USER").build());
        manager
                .createUser(User.builder()
                        .passwordEncoder(password -> password)
                        .username("jane")
                        .password("password")
                        .authorities("USER")
                        .roles("USER").build());
        return manager;
        // @formatter:on
    }

    /**
     * This bean is used to decode the JWT token.
     *
     * @return Returns the JwtDecoder bean to decode JWT tokens.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    /**
     * This bean is used to encode the JWT token.
     *
     * @return Returns the JwtEncoder bean to encode JWT tokens.
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }
}

