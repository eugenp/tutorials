package org.baeldung.web.util;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreated extends ApplicationEvent {
    private final HttpServletResponse response;
    private final long idOfNewResource;

    public ResourceCreated(final Object source, final HttpServletResponse response, final long idOfNewResource) {
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
