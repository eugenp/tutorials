package org.baeldung.boot.service;

import org.baeldung.boot.components.FooComponent;
import org.baeldung.boot.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooService {

    @Autowired
    private FooComponent fooComponent;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFooWithId(@PathVariable Integer id) throws Exception {

        Foo foo = fooComponent.getFooWithId(id);

        return new ResponseEntity<>(foo, HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<?> getFooWithName(@RequestParam String name) throws Exception {

        Foo foo = fooComponent.getFooWithName(name);

        return new ResponseEntity<>(foo, HttpStatus.OK);
    }
}