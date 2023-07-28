package com.baeldung.graphql.server;

import com.baeldung.graphql.data.Book;
import com.baeldung.graphql.data.BookRepository;
import com.baeldung.graphqlreturnmap.entity.Product;

import java.util.List;

import graphql.kickstart.tools.GraphQLQueryResolver;

public class GraphQLQuery implements GraphQLQueryResolver {

    private final BookRepository repository;

    public GraphQLQuery(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> allBooks() {
        return repository.getAllBooks();
    }

    public List<Product> getProducts(int pageSize, int pageNumber) {
        return null;
    }

    public Product getProduct(int id) {
        return null;
    }

}
