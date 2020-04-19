package com.baeldung.validations.functional.handlers.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.validations.functional.handlers.AbstractValidationHandler;
import com.baeldung.validations.functional.model.OtherEntity;
import com.baeldung.validations.functional.validators.OtherEntityValidator;

import reactor.core.publisher.Mono;

@Component
public class OtherEntityValidationHandler extends AbstractValidationHandler<OtherEntity, OtherEntityValidator> {

    private OtherEntityValidationHandler() {
        super(OtherEntity.class, new OtherEntityValidator());
    }

    @Override
    protected Mono<ServerResponse> processBody(OtherEntity validBody, ServerRequest originalRequest) {
        String responseBody = String.format("Other object with item %s and quantity %s!", validBody.getItem(), validBody.getQuantity());
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(responseBody), String.class);
    }
}
