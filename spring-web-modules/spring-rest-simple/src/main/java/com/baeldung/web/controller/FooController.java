package com.baeldung.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.web.dto.Foo;
import com.baeldung.web.dto.FooProtos;
import com.google.common.collect.Lists;

@Controller
public class FooController {

    public FooController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/foos")
    @ResponseBody
    public List<Foo> findListOfFoo() {
        return Lists.newArrayList(new Foo(1, randomAlphabetic(4)));
    }

    // API - read

    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id) {
        return new Foo(id, randomAlphabetic(4));
    }

    // API - write

    @RequestMapping(method = RequestMethod.PUT, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo updateFoo(@PathVariable("id") final String id, @RequestBody final Foo foo) {
        return foo;
    }
    
    @RequestMapping(method = RequestMethod.PATCH, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo patchFoo(@PathVariable("id") final String id, @RequestBody final Foo foo) {
        return foo;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo postFoo(@RequestBody final Foo foo) {
        return foo;
    }
    
    @RequestMapping(method = RequestMethod.HEAD, value = "/foos")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo headFoo() {
        return new Foo(1, randomAlphabetic(4));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}", produces = { "application/x-protobuf" })
    @ResponseBody
    public FooProtos.Foo findProtoById(@PathVariable final long id) {
        return FooProtos.Foo.newBuilder()
            .setId(1)
            .setName("Foo Name")
            .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/foos/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo createFoo(@RequestBody final Foo foo) {
        return foo;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public long deleteFoo(@PathVariable final long id) {
        return id;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/foos/form")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String submitFoo(@RequestParam("id") String id) {
        return id;
    }

}
