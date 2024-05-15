package com.baeldung.resttemplate.configuration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

import com.baeldung.resttemplate.web.dto.Foo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

@Controller
public class FooController {
    
    private Map<Long, Foo> fooRepository = Maps.newHashMap(ImmutableMap.of(1L, new Foo(1L, randomAlphabetic(4))));

    public FooController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/foos")
    @ResponseBody
    public Collection<Foo> findListOfFoo() {
        return fooRepository.values();
    }

    // API - read

    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id) throws HttpClientErrorException {
        Foo foo = fooRepository.get(id);
        
        if (foo == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else {
            return foo;
        }    
    }

    // API - write

    @RequestMapping(method = RequestMethod.PUT, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo updateFoo(@PathVariable("id") final long id, @RequestBody final Foo foo) {
        fooRepository.put(id, foo);
        return foo;
    }
    
    @RequestMapping(method = RequestMethod.PATCH, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo patchFoo(@PathVariable("id") final long id, @RequestBody final Foo foo) {
        fooRepository.put(id, foo);
        return foo;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Foo> postFoo(@RequestBody final Foo foo) {
        
        fooRepository.put(foo.getId(), foo);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/foos/{id}")
                .build()
                .expand(foo.getId())
                .toUri();
        
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        
        final ResponseEntity<Foo> entity = new ResponseEntity<Foo>(foo, headers, HttpStatus.CREATED);
        return entity;
    }
    
    @RequestMapping(method = RequestMethod.HEAD, value = "/foos")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo headFoo() {
        return new Foo(1, randomAlphabetic(4));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/foos/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo createFoo(@RequestBody final Foo foo) {
        fooRepository.put(foo.getId(), foo);
        return foo;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public long deleteFoo(@PathVariable final long id) {
        fooRepository.remove(id);
        return id;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/foos/form", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String submitFoo(@RequestParam("id") String id) {
        return id;
    }

}
