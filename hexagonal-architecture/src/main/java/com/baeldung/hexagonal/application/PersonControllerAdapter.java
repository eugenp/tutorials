package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.domain.PersonPort;
import com.baeldung.hexagonal.domain.dto.CreatePersonDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/people")
class PersonControllerAdapter {

    private final PersonPort personPort;

    PersonControllerAdapter(PersonPort personPort) {
        this.personPort = personPort;
    }

    @PostMapping
    public UUID create(@RequestBody CreatePersonDto request) {
        final UUID id = UUID.randomUUID();
        personPort.save(id, request);
        return id;
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable UUID id) {
        return personPort.getById(id);
    }
}
