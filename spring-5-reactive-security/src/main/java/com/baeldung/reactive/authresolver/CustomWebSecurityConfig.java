package com.baeldung.reactive.authresolver;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class CustomWebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
          .authorizeExchange()
          .pathMatchers("/**")
          .authenticated()
          .and()
          .httpBasic()
          .disable()
          .addFilterAfter(authenticationWebFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
          .build();
    }

    public AuthenticationWebFilter authenticationWebFilter() {
        return new AuthenticationWebFilter(resolver());
    }

    public ReactiveAuthenticationManagerResolver<ServerWebExchange> resolver() {
        return exchange -> {
            if (exchange
              .getRequest()
              .getPath()
              .subPath(0)
              .value()
              .startsWith("/employee")) {
                return Mono.just(employeesAuthenticationManager());
            }
            return Mono.just(customersAuthenticationManager());
        };
    }

    public ReactiveAuthenticationManager customersAuthenticationManager() {
        return authentication -> customer(authentication)
          .switchIfEmpty(Mono.error(new UsernameNotFoundException(authentication
            .getPrincipal()
            .toString())))
          .map(b -> new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
              authentication.getCredentials(),
              Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            )
          );
    }

    public ReactiveAuthenticationManager employeesAuthenticationManager() {
        return authentication -> employee(authentication)
          .switchIfEmpty(Mono.error(new UsernameNotFoundException(authentication
            .getPrincipal()
            .toString())))
          .map(
            b -> new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
              authentication.getCredentials(),
              Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            )
          );
    }

    public Mono<String> customer(Authentication authentication) {
        return Mono.justOrEmpty(authentication
          .getPrincipal()
          .toString()
          .startsWith("customer") ? authentication
          .getPrincipal()
          .toString() : null);
    }

    public Mono<String> employee(Authentication authentication) {
        return Mono.justOrEmpty(authentication
          .getPrincipal()
          .toString()
          .startsWith("employee") ? authentication
          .getPrincipal()
          .toString() : null);
    }
}
