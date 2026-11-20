package com.baeldung.hateoas.event;

import org.springframework.context.ApplicationEvent;

import jakarta.servlet.http.HttpServletResponse;

public class ResourceCreatedEvent extends ApplicationEvent {
    private final HttpServletResponse response;
    private final long idOfNewResource;

    public ResourceCreatedEvent(final Object source, final HttpServletResponse response, final long idOfNewResource) {
        super(source);

        this.response = response;
        this.idOfNewResource = idOfNewResource;
    }

    // API

    public HttpServletResponse getResponse() {
        return response;
    }

    public long getIdOfNewResource() {
        return idOfNewResource;
    }

}
