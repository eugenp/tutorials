package com.baeldung.web;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.persistence.FooRepository;

@RestController("/foos")
public class FooController {

    @Autowired
    private FooRepository repo;

    // API - read

    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    @Validated
    public Foo findById(@PathVariable @Min(0) final long id) {
        return repo.findOne(id).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Foo> findAll() {
        return repo.findAll();
    }

    @RequestMapping(params = { "page", "size" }, method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public List<Foo> findPaginated(@RequestParam("page") @Min(0) final int page, @Max(100) @RequestParam("size") final int size) {
        final Page<Foo> resultPage = repo.findAll(new PageRequest(page, size));
        return resultPage.getContent();
    }

    // API - write

    @RequestMapping(method = RequestMethod.PUT, value = "/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo update(@PathVariable("id") final String id, @RequestBody final Foo foo) {
        return foo;
    }

}
