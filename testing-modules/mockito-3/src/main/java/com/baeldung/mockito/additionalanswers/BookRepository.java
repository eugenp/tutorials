package com.baeldung.mockito.additionalanswers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookRepository {
    public Book getByBookId(Long bookId) {
        return new Book(bookId, "To Kill a Mocking Bird", "Harper Lee", 256);
    }

    public Book save(Book book) {
        return new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getNumberOfPages());
    }

    public Book selectRandomBook(Book bookOne, Book bookTwo, Book bookThree) {
        List<Book> selection = new ArrayList<>();
        selection.add(bookOne);
        selection.add(bookTwo);
        selection.add(bookThree);
        Random random = new Random();
        return selection.get(random.nextInt(selection.size()));
    }
}
