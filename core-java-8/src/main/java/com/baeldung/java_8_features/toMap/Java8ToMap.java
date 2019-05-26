package com.baeldung.java_8_features.toMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8ToMap {
    public Map<String, String> listToMap() {
        java.util.Map<java.lang.String, java.lang.String> bookMap = getBookList().stream().collect(Collectors.toMap(Book::getIsbn, Book::getName));
        System.out.println(bookMap);
        return bookMap;
    }

    public Map<Integer, Book> listToMapWithDupKeyError() {
        Map<Integer, Book> bookMap = getBookList().stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity()));
        System.out.println(bookMap);
        return bookMap;
    }

    public Map<Integer, Book> listToMapWithDupKey() {
        return getBookList().stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity(),
                (o1, o2) -> o1));
    }

    public Map<Integer, Book> listToConcurrentMap() {
        return getBookList().stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
    }

    public Map<String, Book> listToSortedMap() {
        return getBookList().stream()
                .sorted(Comparator.comparing(Book::getName))
                .collect(Collectors.toMap(Book::getName, Function.identity(), (o1, o2) -> o1, TreeMap::new));
    }

    private List<Book> getBookList() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("The Fellowship of the Ring", 1954, "0395489318"));
        books.add(new Book("The Two Towers", 1954, "0345339711"));
        books.add(new Book("The Return of the King", 1955, "0618129111"));

        return books;
    }
}