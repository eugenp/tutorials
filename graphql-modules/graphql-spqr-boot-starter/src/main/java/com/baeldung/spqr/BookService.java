package com.baeldung.spqr;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@GraphQLApi
public class BookService implements IBookService {

    private static final Set<Book> BOOKS_DATA = initializeData();

    @GraphQLQuery(name = "getBookWithTitle")
    public Book getBookWithTitle(@GraphQLArgument(name = "title") String title) {
        return BOOKS_DATA.stream()
            .filter(book -> book.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }

    @GraphQLQuery(name = "getAllBooks", description = "Get all books")
    public List<Book> getAllBooks() {
        return new ArrayList<>(BOOKS_DATA);
    }

    @GraphQLMutation(name = "addBook")
    public Book addBook(@GraphQLArgument(name = "newBook") Book book) {
        BOOKS_DATA.add(book);
        return book;
    }

    @GraphQLMutation(name = "updateBook")
    public Book updateBook(@GraphQLArgument(name = "modifiedBook") Book book) {
        BOOKS_DATA.removeIf(b -> Objects.equals(b.getId(), book.getId()));
        BOOKS_DATA.add(book);
        return book;
    }

    @GraphQLMutation(name = "deleteBook")
    public boolean deleteBook(@GraphQLArgument(name = "book") Book book) {
        return BOOKS_DATA.remove(book);
    }

    private static Set<Book> initializeData() {
        Book book = new Book(1, "J.R.R. Tolkien", "The Lord of the Rings");
        Set<Book> books = new HashSet<>();
        books.add(book);
        return books;
    }

}