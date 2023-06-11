package com.baeldung.spring.cloud.bootstrap.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SessionSavingPreFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(SessionSavingPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getSession()
            .flatMap(session -> {
                logger.debug("SessionId: {}", session.getId());
                return chain.filter(exchange);
            });
    }
}
