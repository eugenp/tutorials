package baeldung.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class LibraryUnitTest {

    @InjectMocks
    private Library library;

    @Mock
    private BookStorage bookStorage;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void givenAnAvailableBook_whenLendBook_ThenBookIsSavedAsUnavailable() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book("author", "title");

        when(bookStorage.findById(bookId)).thenReturn(Optional.of(book));

        library.lendBook(bookId.toString());

        assertFalse(book.isAvailable());
        verify(bookStorage).save(book);
    }

    @Test
    void givenAnAvailableBook_whenReturnBook_ThenExceptionIsThrown() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book("author", "title");

        when(bookStorage.findById(bookId)).thenReturn(Optional.of(book));

        assertThrows(IllegalArgumentException.class, () -> library.returnBook(bookId.toString()));
    }
}