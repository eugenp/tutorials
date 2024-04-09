package com.baeldung.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class ExceptionalTraceFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest()
            .getPath()
            .toString()
            .equals("/trace-exceptional")) {
            exchange.getRequest()
                .getHeaders()
                .add("traceId", "TRACE-ID");
        }
        return chain.filter(exchange);
    }
}