package com.baeldung.reactive.webflux.server.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.webflux.server.handlers.WebFluxServerRequestHandler;

@Component
public class WebFluxServerRequestRouter {
    
    @Bean
    RouterFunction<ServerResponse> helloRouter(WebFluxServerRequestHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/wfserver/hello-functional"), handler::sayHello);
    }

}
