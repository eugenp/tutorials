package com.baeldung.validations.functional.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.validations.functional.handlers.AbstractValidationHandler;
import com.baeldung.validations.functional.model.AnnotatedRequestEntity;

import reactor.core.publisher.Mono;

@Component
public class AnnotatedRequestEntityValidationHandler extends AbstractValidationHandler<AnnotatedRequestEntity, Validator> {

    private AnnotatedRequestEntityValidationHandler(@Autowired Validator validator) {
        super(AnnotatedRequestEntity.class, validator);
    }

    @Override
    protected Mono<ServerResponse> processBody(AnnotatedRequestEntity validBody, ServerRequest originalRequest) {
        String responseBody = String.format("Hi, %s. Password: %s!", validBody.getUser(), validBody.getPassword());
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(responseBody), String.class);
    }
}
