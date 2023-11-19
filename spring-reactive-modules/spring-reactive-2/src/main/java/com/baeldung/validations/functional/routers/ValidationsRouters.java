package com.baeldung.validations.functional.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.validations.functional.handlers.FunctionalHandler;
import com.baeldung.validations.functional.handlers.impl.AnnotatedRequestEntityValidationHandler;
import com.baeldung.validations.functional.handlers.impl.CustomRequestEntityValidationHandler;
import com.baeldung.validations.functional.handlers.impl.OtherEntityValidationHandler;

@Configuration
public class ValidationsRouters {

    @Bean
    public RouterFunction<ServerResponse> validationsRouter(@Autowired CustomRequestEntityValidationHandler dryHandler,
        @Autowired FunctionalHandler complexHandler,
        @Autowired OtherEntityValidationHandler otherHandler,
        @Autowired AnnotatedRequestEntityValidationHandler annotatedEntityHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/complex-handler-functional-validation"), complexHandler::handleRequest)
            .andRoute(RequestPredicates.POST("/dry-functional-validation"), dryHandler::handleRequest)
            .andRoute(RequestPredicates.POST("/other-dry-functional-validation"), otherHandler::handleRequest)
            .andRoute(RequestPredicates.POST("/annotated-functional-validation"), annotatedEntityHandler::handleRequest);
    }
}
