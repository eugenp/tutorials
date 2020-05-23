package baeldung.service;

import baeldung.model.Book;

import java.util.List;

public interface LibraryService {

    Book addBook(String title, String authorName);

    void lendBook(String bookId);

    void returnBook(String bookId);

    List<Book> searchBook(String query);

}
