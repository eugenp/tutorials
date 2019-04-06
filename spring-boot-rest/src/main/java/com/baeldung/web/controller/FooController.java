package com.baeldung.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IFooService;
import com.baeldung.web.exception.MyResourceNotFoundException;
import com.baeldung.web.hateoas.event.PaginatedResultsRetrievedEvent;
import com.baeldung.web.hateoas.event.ResourceCreatedEvent;
import com.baeldung.web.hateoas.event.SingleResourceRetrievedEvent;
import com.baeldung.web.util.RestPreconditions;
import com.google.common.base.Preconditions;

@RestController
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
    
    // Note: the global filter overrides the ETag value we set here. We can still analyze its behaviour in the Integration Test.
    @GetMapping(value = "/{id}/custom-etag")
    public ResponseEntity<Foo> findByIdWithCustomEtag(@PathVariable("id") final Long id,
        final HttpServletResponse response) {
        final Foo foo = RestPreconditions.checkFound(service.findById(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return ResponseEntity.ok()
            .eTag(Long.toString(foo.getVersion()))
            .body(foo);
    }

    // read - one

    @GetMapping(value = "/{id}")
    public Foo findById(@PathVariable("id") final Long id, final HttpServletResponse response) {
        try {
            final Foo resourceById = RestPreconditions.checkFound(service.findById(id));

            eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
            return resourceById;
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", exc);
        }

    }

    // read - all

    @GetMapping
    public List<Foo> findAll() {
        return service.findAll();
    }

    @GetMapping(params = { "page", "size" })
    public List<Foo> findPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size,
        final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<Foo> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(Foo.class, uriBuilder, response, page,
            resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }

    @GetMapping("/pageable")
    public List<Foo> findPaginatedWithPageable(Pageable pageable, final UriComponentsBuilder uriBuilder,
        final HttpServletResponse response) {
        final Page<Foo> resultPage = service.findPaginated(pageable);
        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(Foo.class, uriBuilder, response,
            pageable.getPageNumber(), resultPage.getTotalPages(), pageable.getPageSize()));

        return resultPage.getContent();
    }

    // write

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Foo create(@RequestBody final Foo resource, final HttpServletResponse response) {
        Preconditions.checkNotNull(resource);
        final Foo foo = service.create(resource);
        final Long idOfCreatedResource = foo.getId();

        eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));

        return foo;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @RequestBody final Foo resource) {
        Preconditions.checkNotNull(resource);
        RestPreconditions.checkFound(service.findById(resource.getId()));
        service.update(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") final Long id) {
        service.deleteById(id);
    }
}
