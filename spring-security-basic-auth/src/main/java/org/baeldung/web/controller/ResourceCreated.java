package org.baeldung.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreated extends ApplicationEvent {
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    private final long idOfNewResource;

    public ResourceCreated(final Object source, final HttpServletRequest request, final HttpServletResponse response, final long idOfNewResource) {
        super(source);

        this.request = request;
        this.response = response;
        this.idOfNewResource = idOfNewResource;
    }

    // API

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public long getIdOfNewResource() {
        return idOfNewResource;
    }

}
