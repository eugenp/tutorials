package com.shaheen.hexa.rest;


import com.shaheen.hexa.core.usecase.CreatePersonCommand;
import com.shaheen.hexa.core.usecase.CreatePersonUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.CREATED;

public class CreatePersonController {
    private final CreatePersonUseCase createPersonUseCase;

    @Autowired
    public CreatePersonController(final CreatePersonUseCase createPersonUseCase) {
        this.createPersonUseCase = createPersonUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createPerson(@RequestBody final CreatePersonCommand command) {
        return new ResponseEntity<>(createPersonUseCase.createPerson(command), CREATED);
    }
}
