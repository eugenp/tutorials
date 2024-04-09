package com.baeldung.router;

import com.baeldung.filters.TraceHandlerFilterFunction;
import com.baeldung.handler.TraceRouterHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TraceRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(TraceRouterHandler routerHandler) {
        return route(GET("/trace-functional-filter"), routerHandler::handle).filter(new TraceHandlerFilterFunction())
            .and(route().GET("/trace-functional-before", routerHandler::handle)
                .before(request -> ServerRequest.from(request)
                    .header("traceId", "FUNCTIONAL-TRACE-ID")
                    .build())
                .build());
    }
}
