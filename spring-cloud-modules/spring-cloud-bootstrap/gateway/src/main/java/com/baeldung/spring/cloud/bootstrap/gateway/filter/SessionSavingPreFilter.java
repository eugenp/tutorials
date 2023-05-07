package com.baeldung.spring.cloud.bootstrap.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SessionSavingPreFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange.getSession()
            .map(webSession -> {
                return exchange.mutate()
                    .request(exchange.getRequest()
                        .mutate()
                        .header("Cookie", "SESSION=" + webSession.getId())
                        .build())
                    .build();
            })
            .block());
    }
}
