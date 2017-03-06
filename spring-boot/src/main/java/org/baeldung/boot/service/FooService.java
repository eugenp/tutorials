package org.baeldung.boot.service;

import org.baeldung.boot.components.FooComponent;
import org.baeldung.boot.exceptions.CommonException;
import org.baeldung.boot.exceptions.FooNotFoundException;
import org.baeldung.boot.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooService {

    @Autowired
    private FooComponent fooComponent;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFooWithId(@PathVariable Integer id) {

        Foo foo = fooComponent.getFooWithId(id);

        return new ResponseEntity<Foo>(foo, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFooWithName(@RequestParam String name) {

        Foo foo = fooComponent.getFooWithName(name);

        return new ResponseEntity<Foo>(foo, HttpStatus.OK);
    }

    @ExceptionHandler(value = FooNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Foo not found")
    public void handleFooNotFoundException() {

    }

    @ExceptionHandler(value = CommonException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Common exception")
    public void handleCommonException() {

    }
}