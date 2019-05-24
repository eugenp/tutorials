package org.hexagonal.architechture.example;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hexagonal.architechture.core.Book;
import org.hexagonal.architechture.core.BookGallery;
import org.hexagonal.architechture.core.BookRepository;
import org.hexagonal.architechture.core.BookViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookGalleryUnitTest {
    private static final String BOOK_NAME = "Gold Rush";
    private final List<String> booksDisplayed = new ArrayList<>();

    private BookGallery sut;

    @BeforeEach
    public void setUp() {
        BookRepository respository = new TestBookRespository();
        BookViewer viewer = new TestConsoleBookViewer();
        sut = new BookGallery(viewer, respository);
    }

    @Test
    public void whenDisplayAllBooks_thenReadsFromTestBookRespository() {
        sut.displayAllBooks();
        assertTrue(booksDisplayed.contains(BOOK_NAME));
    }

    // Mocks 
    
    class TestBookRespository implements BookRepository {
        @Override
        public List<Book> getAllBooks() {
            return Arrays.asList(new Book(BOOK_NAME));
        }
    }

    class TestConsoleBookViewer implements BookViewer {

        @Override
        public void render(Book book) {
            booksDisplayed.add(book.getName());
        }

    }

}
