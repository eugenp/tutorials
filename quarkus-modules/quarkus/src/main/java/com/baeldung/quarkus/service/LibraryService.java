package com.baeldung.quarkus.service;

import com.baeldung.quarkus.model.Book;
import com.baeldung.quarkus.repository.BookRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Transactional
@ApplicationScoped
public class LibraryService {

    @Inject
    BookRepository bookRepository;

    public Set<Book> find(String query) {
        if (query == null) {
            return bookRepository.findAll().stream().collect(toSet());
        }

        return bookRepository.findBy(query).collect(toSet());
    }

}
