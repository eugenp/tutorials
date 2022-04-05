package com.baeldung.web.hateoas.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

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