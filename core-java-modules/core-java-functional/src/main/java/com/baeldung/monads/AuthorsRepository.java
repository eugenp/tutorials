package com.baeldung.monads;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import reactor.core.publisher.Mono;

public class AuthorsRepository {

    private static List<Author> authors = listOf(
        new Author("John Doe", 1L, listOf("Fiction", "Thriller"), listOf(
            new Book("Book 1", 1L, "Fiction"),
            new Book("Book 2", 2L, "Thriller")
        )));

    public static Mono<Author> findAuthorByName(String authorName) {
        Optional<Author> author = authors.stream()
            .filter(it -> it.getName()
                .equals(authorName))
            .findFirst();

        return Mono.justOrEmpty(author);
    }

    public static Mono<Book> findLatestBookByAuthorId(Long authorId) {
        Optional<Author> author = authors.stream()
            .filter(it -> it.getAuthorId()
                .equals(authorId))
            .findFirst();

        Optional<Book> book = author.flatMap(it -> it.getBooks()
            .stream()
            .findFirst());

        return Mono.justOrEmpty(book);
    }

    public static Author findById(Long authorId) {
        return authors.stream()
            .filter(author -> author.getAuthorId().equals(authorId))
            .findFirst()
            .orElse(null);
    }

    public static class AuthorNotFoundException extends RuntimeException {
        public AuthorNotFoundException(String message) {
            super(message);
        }

        public AuthorNotFoundException() {
            super();
        }
    }

    static <T> List<T> listOf(T... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) {
            list.add(item);
        }
        return list;
    }

}
