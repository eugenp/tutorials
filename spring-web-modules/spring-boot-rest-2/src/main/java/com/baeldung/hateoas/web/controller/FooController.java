package com.baeldung.hateoas.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.hateoas.event.ResourceCreatedEvent;
import com.baeldung.hateoas.event.SingleResourceRetrievedEvent;
import com.baeldung.hateoas.persistence.model.Foo;
import com.baeldung.hateoas.persistence.service.IFooService;

import com.baeldung.hateoas.web.exception.MyResourceNotFoundException;

import com.baeldung.hateoas.util.RestPreconditions;
import com.google.common.base.Preconditions;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/foos")
public class FooController {

    private static final Logger logger = LoggerFactory.getLogger(FooController.class);
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IFooService service;

    public FooController() {
        super();
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


}
