package com.baeldung.web;

import com.baeldung.persistence.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController("/foos")
public class FooController {

    @PostConstruct
    public void init(){
        System.out.println("test");
    }

    @Autowired
    private FooRepository repo;

    // API - read

    @GetMapping("/foos/{id}")
    @ResponseBody
    @Validated
    public Foo findById(@PathVariable @Min(0) final long id) {
        return repo.findById(id)
            .orElse(null);
    }

    @GetMapping
    @ResponseBody
    public List<Foo> findAll() {
        return repo.findAll();
    }

    @GetMapping(params = { "page", "size" })
    @ResponseBody
    @Validated
    public List<Foo> findPaginated(@RequestParam("page") @Min(0) final int page, @Max(100) @RequestParam("size") final int size) {
        return repo.findAll(PageRequest.of(page, size))
            .getContent();
    }

    // API - write

    @PutMapping("/foos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo update(@PathVariable("id") final String id, @RequestBody final Foo foo) {
        return foo;
    }

}
