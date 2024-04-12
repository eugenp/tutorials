package com.baeldung.formlogin.controller;

import com.baeldung.formlogin.model.Foo;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
@RequestMapping(value = "foos", produces = MediaType.APPLICATION_JSON_VALUE)
@OpenAPIDefinition(info = @Info(title = "Foos API", version = "v1"))
public class FooController {

    private static final int STRING_LENGTH = 6;

    @GetMapping(value = "/{id}")
    public Foo findById(@PathVariable("id") final Long id) {
        return new Foo(randomAlphabetic(STRING_LENGTH));
    }

    @GetMapping
    public List<Foo> findAll() {
        return Lists.newArrayList(new Foo(randomAlphabetic(STRING_LENGTH)), new Foo(randomAlphabetic(STRING_LENGTH)), new Foo(randomAlphabetic(STRING_LENGTH)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Foo create(@RequestBody final Foo foo) {
        return foo;
    }
}
