package com.baeldung.reactive.handler;

import com.baeldung.reactive.generator.EmailGenerator;
import com.baeldung.reactive.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EmailHandler {

    @Autowired EmailGenerator emailGenerator;

    public Mono<ServerResponse> getEmails(ServerRequest serverRequest) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(emailGenerator.fetchQuoteStream(), Email.class);
    }
}
