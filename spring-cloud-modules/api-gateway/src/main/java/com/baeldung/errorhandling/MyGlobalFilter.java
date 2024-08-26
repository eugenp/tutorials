package com.baeldung.errorhandling;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
class MyGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (hasReachedRateLimit(exchange)) {
            throw new RateLimitRequestException("Too many requests");
        }

        return chain.filter(exchange);
    }

    private boolean hasReachedRateLimit(ServerWebExchange exchange) {
        // Simulates the rate limit being reached
        return exchange.getRequest().getURI().getPath().contains("/test/custom_rate_limit") && (!exchange.getRequest().getHeaders().containsKey("X-RateLimit-Remaining") ||
                Integer.parseInt(exchange.getRequest().getHeaders().getFirst("X-RateLimit-Remaining")) <= 0);
    }
}
