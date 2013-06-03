package org.baeldung.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrieved> {

    @Override
    public void onApplicationEvent(final SingleResourceRetrieved resourceRetrievedEvent) {
        Preconditions.checkNotNull(resourceRetrievedEvent);

        final HttpServletRequest request = resourceRetrievedEvent.getRequest();
        final HttpServletResponse response = resourceRetrievedEvent.getResponse();
        addLinkHeaderOnSingleResourceRetrieval(request, response);
    }

    void addLinkHeaderOnSingleResourceRetrieval(final HttpServletRequest request, final HttpServletResponse response) {
        final StringBuffer requestURL = request.getRequestURL();
        final int positionOfLastSlash = requestURL.lastIndexOf("/");
        final String uriForResourceCreation = requestURL.substring(0, positionOfLastSlash);

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, "collection");
        response.addHeader("Link", linkHeaderValue);
    }

}