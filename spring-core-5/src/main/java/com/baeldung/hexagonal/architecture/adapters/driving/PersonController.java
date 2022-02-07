package com.baeldung.hexagonal.architecture.adapters.driving;

import com.baeldung.hexagonal.architecture.core.domain.Person;
import com.baeldung.hexagonal.architecture.core.ports.inbound.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("persons")
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    @GetMapping
    @RequestMapping("{id}")
    public Person getById(@PathVariable Long id) {
        try {
            return personService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }
}
