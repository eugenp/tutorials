package org.baeldung.web.hateoas.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.baeldung.web.hateoas.event.SingleResourceRetrievedEvent;
import org.baeldung.web.util.LinkUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

@Component
class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    @Override
    public void onApplicationEvent(final SingleResourceRetrievedEvent resourceRetrievedEvent) {
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
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}