package org.baeldung.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.baeldung.persistence.service.FooService;
import org.baeldung.web.dto.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;

@Controller
public class FooController {

    @Autowired
    private FooService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // API

    @RequestMapping(value = "admin/foo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Foo get(@PathVariable("id") final Long id, final HttpServletRequest request, final HttpServletResponse response) {
        final Foo resourceById = Preconditions.checkNotNull(service.getById(id));

        eventPublisher.publishEvent(new SingleResourceRetrieved(this, request, response));
        return resourceById;
    }

    @RequestMapping(value = "admin/foo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final Foo resource, final HttpServletRequest request, final HttpServletResponse response) {
        Preconditions.checkNotNull(resource);
        final Long idOfCreatedResource = service.create(resource);

        eventPublisher.publishEvent(new ResourceCreated(this, request, response, idOfCreatedResource));
    }

}
