package org.baeldung.boot.service;

import org.baeldung.boot.components.FooService;
import org.baeldung.boot.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @Autowired
    private FooService fooService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFooWithId(@PathVariable Integer id) throws Exception {

        Foo foo = fooService.getFooWithId(id);

        return new ResponseEntity<>(foo, HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<?> getFooWithName(@RequestParam String name) throws Exception {

        Foo foo = fooService.getFooWithName(name);

        return new ResponseEntity<>(foo, HttpStatus.OK);
    }
}