package org.baeldung.web.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
import org.baeldung.web.exception.MyResourceNotFoundException;
import org.baeldung.web.util.LinkUtil;
import org.baeldung.web.util.ResourceCreated;
import org.baeldung.web.util.RestPreconditions;
import org.baeldung.web.util.SingleResourceRetrieved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import com.google.common.base.Preconditions;

@Controller
@RequestMapping(value = "/foos")
public class FooController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IFooService service;

    public FooController() {
        super();
    }

    // API

    // read - one

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Foo findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletRequest request, final HttpServletResponse response) {
        final Foo resourceById = RestPreconditions.checkFound(service.findOne(id));
        eventPublisher.publishEvent(new SingleResourceRetrieved(this, request, response));
        return resourceById;
    }

    // read - all

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Foo> findAll() {
        return service.findAll();
    }

    @RequestMapping(params = { "page", "size" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Foo> findPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {

        final Page<Foo> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        // eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(Foo.class, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }

    // discover

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void adminRoot(final HttpServletRequest request, final HttpServletResponse response) {
        final String rootUri = request.getRequestURL().toString();

        final URI fooUri = new UriTemplate("{rootUri}/{resource}").expand(rootUri, "foo");
        final String linkToFoo = LinkUtil.createLinkHeader(fooUri.toASCIIString(), "collection");
        response.addHeader("Link", linkToFoo);
    }

    // write

    @RequestMapping(value = "admin/foo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final Foo resource, final HttpServletRequest request, final HttpServletResponse response) {
        Preconditions.checkNotNull(resource);
        final Long idOfCreatedResource = service.create(resource).getId();

        eventPublisher.publishEvent(new ResourceCreated(this, request, response, idOfCreatedResource));
    }

}
