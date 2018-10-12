package com.baeldung.validations.functional.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.validations.functional.model.CustomRequestEntity;
import com.baeldung.validations.functional.validators.CustomRequestEntityValidator;

import reactor.core.publisher.Mono;

@Component
public class ValidationsHandlers {

    public Mono<ServerResponse> handleRequest(final ServerRequest request) {
        Validator validator = new CustomRequestEntityValidator();
        Mono<String> responseBody = request.bodyToMono(CustomRequestEntity.class)
            .map(cre -> {
                Errors errors = new BeanPropertyBindingResult(cre, CustomRequestEntity.class.getName());
                validator.validate(cre, errors);

                if (errors == null || errors.getAllErrors()
                    .isEmpty()) {
                    return String.format("Hi, %s [%s]!", cre.getName(), cre.getCode());

                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getAllErrors()
                        .toString());
                }

            });
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(responseBody, String.class);

    }
}
