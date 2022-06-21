package com.baeldung.reactive.urlmatch;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

class IndexRewriteFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        if (request.getURI()
            .getPath()
            .equals("/")) {
            return webFilterChain.filter(serverWebExchange.mutate()
                .request(builder -> builder.method(request.getMethod())
                    .contextPath(request.getPath()
                        .toString())
                    .path("/test"))
                .build());
        }
        return webFilterChain.filter(serverWebExchange);
    }

}
