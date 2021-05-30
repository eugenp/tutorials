package com.baeldung.hexarch.boostrore.controller;

import com.baeldung.hexarch.boostrore.controller.dto.Author;
import com.baeldung.hexarch.boostrore.repository.AuthorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthorsController {
    private final AuthorsRepository authorsRepository;

    @PutMapping(value = "/api/v1/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    private void createAuthor(@RequestBody final Author author) {
        com.baeldung.hexarch.boostrore.model.Author author1 = new com.baeldung.hexarch.boostrore.model.Author();
        author1.setLastName(author.getLastName());
        author1.setFirstName(author.getFirstName());
        author1.setEmailId(author.getEmailId());
        authorsRepository.save(author1);
    }
}
