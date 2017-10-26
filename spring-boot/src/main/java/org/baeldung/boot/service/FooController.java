package org.baeldung.boot.service;

import org.baeldung.boot.components.FooService;
import org.baeldung.boot.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @Autowired
    private FooService fooService;

    @GetMapping("/{id}")
    public Foo getFooWithId(@PathVariable Integer id) throws Exception {
        return fooService.getFooWithId(id);
    }

    @GetMapping("/")
    public Foo getFooWithName(@RequestParam String name) throws Exception {
        return fooService.getFooWithName(name);
    }
}