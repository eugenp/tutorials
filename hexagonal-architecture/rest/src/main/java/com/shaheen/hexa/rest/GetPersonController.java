package com.shaheen.hexa.rest;

import com.shaheen.hexa.core.domain.Person;
import com.shaheen.hexa.core.usecase.getperson.GetPersonUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@PersonController
public class GetPersonController {
    private final GetPersonUseCase getPersonUseCase;

    @Autowired
    public GetPersonController(final GetPersonUseCase getPersonUseCase) {
        this.getPersonUseCase = getPersonUseCase;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable final Long id) {
        log.info("Request: /person/get/{}", id);
        return new ResponseEntity<>(getPersonUseCase.getPerson(id), OK);
    }
}
