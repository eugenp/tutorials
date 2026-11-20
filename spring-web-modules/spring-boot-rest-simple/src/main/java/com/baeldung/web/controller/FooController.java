package com.baeldung.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IFooService;



import com.baeldung.web.util.RestPreconditions;
import com.google.common.base.Preconditions;


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
    public Foo findById(@PathVariable("id") final Long id) {
        return RestPreconditions.checkFound(service.findById(id));
    }

    // read - all

    @GetMapping
    public List<Foo> findAll() {
        return service.findAll();
    }

    // write

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Foo create(@RequestBody final Foo resource) {
        Preconditions.checkNotNull(resource);
        return service.create(resource);
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
