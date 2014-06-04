package org.baeldung.web.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

@Component
class ResourceCreatedDiscoverabilityListener implements ApplicationListener<ResourceCreated> {

    @Override
    public void onApplicationEvent(final ResourceCreated resourceCreatedEvent) {
        Preconditions.checkNotNull(resourceCreatedEvent);

        final HttpServletRequest request = resourceCreatedEvent.getRequest();
        final HttpServletResponse response = resourceCreatedEvent.getResponse();
        final long idOfNewResource = resourceCreatedEvent.getIdOfNewResource();

        addLinkHeaderOnResourceCreation(request, response, idOfNewResource);
    }

    void addLinkHeaderOnResourceCreation(final HttpServletRequest request, final HttpServletResponse response, final long idOfNewResource) {
        final String requestUrl = request.getRequestURL().toString();
        final URI uri = new UriTemplate("{requestUrl}/{idOfNewResource}").expand(requestUrl, idOfNewResource);
        response.setHeader(HttpHeaders.LOCATION, uri.toASCIIString());
    }

}