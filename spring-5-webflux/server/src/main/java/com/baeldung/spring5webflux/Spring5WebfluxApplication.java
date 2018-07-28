package com.baeldung.spring5webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@SpringBootApplication
public class Spring5WebfluxApplication {
    @Bean
    RouterFunction<ServerResponse> routerFunctions(LocationEventHandler eventHandler) {
        return RouterFunctions.route(GET("/locations"), eventHandler::locationEvents);
    }
    @Bean
    LocationEventHandler locationEventHandler() {
        return new LocationEventHandler(new LocationEventService());
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring5WebfluxApplication.class, args);
    }
}
