package org.baeldung.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.web.dto.Foo;
import org.baeldung.web.dto.FooProtos;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FooController {

    public FooController() {
        super();
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
