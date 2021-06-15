package services;

import models.Book;

import java.util.List;

public interface BookService {

    void saveBook(Book book);
    List<Book> getBooks();
}
