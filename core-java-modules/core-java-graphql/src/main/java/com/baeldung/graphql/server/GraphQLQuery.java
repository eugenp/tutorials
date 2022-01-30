package com.baeldung.graphql.server;

import com.baeldung.graphql.data.Book;
import com.baeldung.graphql.data.BookRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.List;

public class GraphQLQuery implements GraphQLQueryResolver {

    private final BookRepository repository;

    public GraphQLQuery(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> allBooks() {
        return repository.getAllBooks();
    }

}
