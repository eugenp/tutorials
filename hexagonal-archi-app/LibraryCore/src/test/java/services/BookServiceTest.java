package services;

import models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import services.implementation.BookServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private BookService bookService;

    @Before
    public void setUp() throws Exception {
        bookService = new BookServiceImpl();
    }

    @Test
    public void saveBook() {
        Book book = mockBook();
        bookService.saveBook(book);

        assertEquals(bookService.getBooks().get(2).getTitle(), "Half Of A Yellow Sun");
    }

    @Test
    public void getBooks() {
        Book book = mockBook();
        bookService.saveBook(book);

        assertEquals(bookService.getBooks().get(2), book);
    }

    private Book mockBook() {
        Book book = new Book();
        book.setId(2l);
        book.setTitle("Half Of A Yellow Sun");
        return book;
    }

}
