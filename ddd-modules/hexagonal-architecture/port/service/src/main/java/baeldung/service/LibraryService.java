package baeldung.service;

import baeldung.model.Book;
import baeldung.model.BookStorage;
import baeldung.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final Library library;
    private final BookProviderNotificationService bookProviderNotificationService;

    @Autowired
    public LibraryService(BookStorage bookStorage, BookProviderNotificationService bookProviderNotificationService) {
        this.library = new Library(bookStorage);
        this.bookProviderNotificationService = bookProviderNotificationService;
    }

    public Book addBook(String title, String authorName) {
        return library.add(new Book(title, authorName));
    }

    public void lendBook(String bookId) {
        library.lendBook(bookId);
    }

    public void returnBook(String bookId) {
        library.returnBook(bookId);
    }

    public List<Book> searchBook(String query) {
        List<Book> searchResults = library.search(query);

        if (searchResults.isEmpty()) {
            bookProviderNotificationService.notifyMissingBook(query);
        } else if (searchResults.stream().noneMatch(Book::isAvailable)) {
            bookProviderNotificationService.notifyNotEnoughBooks(query, searchResults.size());
        }

        return searchResults;
    }

}
