package com.baeldung.formlogin.controller;

import com.baeldung.formlogin.model.FooDTO;
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
    public FooDTO findById(@PathVariable("id") final Long id) {
        return new FooDTO(randomAlphabetic(STRING_LENGTH));
    }

    @GetMapping
    public List<FooDTO> findAll() {
        return Lists.newArrayList(new FooDTO(randomAlphabetic(STRING_LENGTH)), new FooDTO(randomAlphabetic(STRING_LENGTH)), new FooDTO(randomAlphabetic(STRING_LENGTH)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FooDTO create(@RequestBody final FooDTO fooDTO) {
        return fooDTO;
    }
}
