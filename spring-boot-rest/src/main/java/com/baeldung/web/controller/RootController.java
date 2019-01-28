package com.baeldung.web.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import com.baeldung.web.util.LinkUtil;

@Controller
@RequestMapping(value = "/auth/")
public class RootController {

    public RootController() {
        super();
    }

    // API

    // discover

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void adminRoot(final HttpServletRequest request, final HttpServletResponse response) {
        final String rootUri = request.getRequestURL()
            .toString();

        final URI fooUri = new UriTemplate("{rootUri}/{resource}").expand(rootUri, "foo");
        final String linkToFoo = LinkUtil.createLinkHeader(fooUri.toASCIIString(), "collection");
        response.addHeader("Link", linkToFoo);
    }

}
