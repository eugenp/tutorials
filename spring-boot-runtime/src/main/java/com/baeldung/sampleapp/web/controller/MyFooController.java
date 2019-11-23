package com.baeldung.sampleapp.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baeldung.sampleapp.web.dto.Foo;
import com.baeldung.sampleapp.web.exception.ResourceNotFoundException;

@Controller
@RequestMapping(value = "/foos")
public class MyFooController {

    private final Map<Long, Foo> myfoos;

    public MyFooController() {
        super();
        myfoos = new HashMap<Long, Foo>();
        myfoos.put(1L, new Foo(1L, "sample foo"));
    }

    // API - read

    @RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public Collection<Foo> findAll() {
        return myfoos.values();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = { "application/json" })
    @ResponseBody
    public Foo findById(@PathVariable final long id) {
        final Foo foo = myfoos.get(id);
        if (foo == null) {
            throw new ResourceNotFoundException();
        }
        return foo;
    }

    // API - write

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo updateFoo(@PathVariable("id") final long id, @RequestBody final Foo foo) {
        myfoos.put(id, foo);
        return foo;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFoo2(@PathVariable("id") final long id, @RequestBody final Foo foo) {
        myfoos.put(id, foo);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo createFoo(@RequestBody final Foo foo, HttpServletResponse response) {
        myfoos.put(foo.getId(), foo);
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/" + foo.getId())
            .toUriString());
        return foo;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable final long id) {
        myfoos.remove(id);
    }

}
