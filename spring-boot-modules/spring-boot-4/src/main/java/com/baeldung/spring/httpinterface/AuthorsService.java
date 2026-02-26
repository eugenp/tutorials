package com.baeldung.spring.httpinterface;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

interface AuthorsService {

    @GetExchange("/authors")
    List<Author> getAuthors();

    @GetExchange("/authors/{id}")
    Author getAuthor(@PathVariable("id") long id);

    record Author(String name, long id) {

    }

}
