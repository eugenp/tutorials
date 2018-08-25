package com.baeldung.reactive.realtime.functional.routers;

import com.baeldung.reactive.realtime.functional.handlers.FlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FlowRouterFunctions {

    @Bean
    public RouterFunction<ServerResponse> flowRoute(@Autowired FlowHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/realtime-event/flow-functional"), handler::flow);
    }
}
