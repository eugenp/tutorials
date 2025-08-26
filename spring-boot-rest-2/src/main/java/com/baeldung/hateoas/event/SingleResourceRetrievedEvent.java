package com.baeldung.hateoas.event;

import org.springframework.context.ApplicationEvent;

import jakarta.servlet.http.HttpServletResponse;

public class SingleResourceRetrievedEvent extends ApplicationEvent {
    private final HttpServletResponse response;

    public SingleResourceRetrievedEvent(final Object source, final HttpServletResponse response) {
        super(source);

        this.response = response;
    }

    // API

    public HttpServletResponse getResponse() {
        return response;
    }

}