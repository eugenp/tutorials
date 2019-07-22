package com.baeldung.hexagonal.architecture;

import java.util.List;

public interface BooksDao {
    public List<Book> findAllBooks();
}
