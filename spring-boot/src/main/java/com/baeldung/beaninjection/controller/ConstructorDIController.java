package com.baeldung.beaninjection.controller;

import com.baeldung.beaninjection.model.Author;
import com.baeldung.beaninjection.service.AuthorService;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by ggallo on 23/01/2018.
 */
@Controller
public class ConstructorDIController {

    private AuthorService authorService;

    public ConstructorDIController(AuthorService authorService) {
        this.authorService = authorService;
    }

    public List<Author> getAuthors() {
        return authorService.listAuthors();
    }
}
