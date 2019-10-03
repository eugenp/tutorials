package com.baeldung.batch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.batch.model.Book;

public class BookItemAuthorCounter {

    private Map<String, Integer> authorBookCount = new HashMap<String, Integer>();

    public BookItemAuthorCounter() {
    }

    public void count(List<Book> processedBooks) {

        for (Book b : processedBooks) {
            if (authorBookCount.containsKey(b.getAuthor())) {
                authorBookCount.put(b.getAuthor(), authorBookCount.get(b.getAuthor()) + 1);
            } else {
                authorBookCount.put(b.getAuthor(), 1);
            }
        }
    }

}
