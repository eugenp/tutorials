package org.baeldung.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class SingleResourceRetrieved extends ApplicationEvent {
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public SingleResourceRetrieved(final Object source, final HttpServletRequest request, final HttpServletResponse response) {
        super(source);

        this.request = request;
        this.response = response;
    }

    // API

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

}
