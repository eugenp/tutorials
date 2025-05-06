package com.baeldung.errorhandling;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyCustomFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (isAuthRoute(exchange) && !isAuthorization(exchange)) {
            throw new CustomRequestAuthException("Not authorized");
        }

        return chain.filter(exchange);
    }

    private static boolean isAuthorization(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().containsKey("Authorization");
    }

    private static boolean isAuthRoute(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getPath().equals("/test/custom_auth");
    }
}