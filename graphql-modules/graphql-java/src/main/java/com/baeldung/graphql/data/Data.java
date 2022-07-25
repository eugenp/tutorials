package com.baeldung.graphql.data;

import java.util.Collections;
import java.util.List;

public class Data {

    private List<Book> allBooks;

    public Data() {

    }

    public Data(List<Book> allBooks) {
        this.allBooks = allBooks;
    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(allBooks);
    }

}
