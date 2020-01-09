package com.baeldung.hexagonal.architecture.domain.boundary;

import com.baeldung.hexagonal.architecture.domain.Book;
import java.util.List;

public interface BookService {
    String add(Book book);

    Book searchByIsbn(String isbn);

    List<Book> searchByAuthor(String author);

    List<Book> searchByTitle(String title);
}
