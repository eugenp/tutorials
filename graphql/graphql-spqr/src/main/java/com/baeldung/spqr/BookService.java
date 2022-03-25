package com.baeldung.spqr;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@GraphQLApi
public class BookService implements IBookService {

    Set<Book> books = new HashSet<>();

    @GraphQLQuery(name = "getBookWithTitle")
    public Book getBookWithTitle(@GraphQLArgument(name = "title") String title) {
        return books.stream()
            .filter(book -> book.getTitle()
                .equals(title))
            .findFirst()
            .orElse(null);
    }

    @GraphQLQuery(name = "getAllBooks", description = "Get all books")
    public List<Book> getAllBooks() {
        return books.stream()
            .toList();
    }

    @GraphQLMutation(name = "addBook")
    public Book addBook(@GraphQLArgument(name = "newBook") Book book) {
        books.add(book);
        return book;
    }

    @GraphQLMutation(name = "updateBook")
    public Book updateBook(@GraphQLArgument(name = "modifiedBook") Book book) {
        books.remove(book);
        books.add(book);
        return book;
    }

    @GraphQLMutation(name = "deleteBook")
    public boolean deleteBook(@GraphQLArgument(name = "book") Book book) {
        return books.remove(book);
    }
}