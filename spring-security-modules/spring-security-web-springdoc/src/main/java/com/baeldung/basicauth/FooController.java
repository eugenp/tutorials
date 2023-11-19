package com.baeldung.basicauth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.google.common.collect.Lists;

@RestController
@OpenAPIDefinition(info = @Info(title = "Foos API", version = "v1"))
@SecurityRequirement(name = "basicAuth")
@RequestMapping(value = "foos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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
