package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.model.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggallo on 23/01/2018.
 */

@Service
public class AuthorService {

    public List<Author> listAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(new Author("Gorka", 34));
        authors.add(new Author("Peter", 42));

        return authors;
    }

}
