package baeldung.model;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class Library {

    private final BookStorage bookStorage;

    public final Book add(Book book) {
        return bookStorage.save(book);
    }

    public final void returnBook(String bookId) {
        updateStatus(bookId, true);
    }

    public final void lendBook(String bookId) {
        updateStatus(bookId, false);
    }

    public final List<Book> search(String query) {
        return bookStorage.findByTitleOrAuthor(query);
    }

    private void updateStatus(String bookId, boolean newAvailableStatus) {
        Book book = bookStorage.findById(UUID.fromString(bookId)).orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.isAvailable() == newAvailableStatus) {
            throw new IllegalArgumentException("Book already in requested available status.");
        }

        book.setAvailable(newAvailableStatus);
        bookStorage.save(book);
    }

}
