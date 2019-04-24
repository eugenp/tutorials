package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.BookManagerAdapter;
import com.baeldung.hexagonal.adapters.BookValidatorAdapter;
import com.baeldung.hexagonal.adapters.InMemoryBookRepositoryAdapter;
import com.baeldung.hexagonal.domain.business.BookServiceImpl;
import com.baeldung.hexagonal.domain.models.Book;
import com.baeldung.hexagonal.domain.ports.BookRepository;
import com.baeldung.hexagonal.domain.ports.BookValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApplicationUnitTest {

    private BookManagerAdapter bookManagerAdapter;

    @Before
    public void setup() {
        BookRepository bookRepository = new InMemoryBookRepositoryAdapter();
        BookValidator bookValidator = new BookValidatorAdapter();
        BookServiceImpl bookService = new BookServiceImpl(bookRepository, bookValidator);
        bookManagerAdapter = new BookManagerAdapter(bookService);
    }

    @Test
    public void whenBookIsAdded_ThenCorrectBookIsReturned() {
        Book book = createValidBook();

        int savedBook = bookManagerAdapter.addBook(book);

        Assert.assertTrue(bookManagerAdapter.getBook(savedBook).isPresent());
    }

    @Test
    public void whenInvalidBookIsAdded_ThenNoBookIsReturned() {
        Book book = createInvalidBook();

        int savedBook = bookManagerAdapter.addBook(book);

        Assert.assertTrue(savedBook == -1);
        Assert.assertFalse(bookManagerAdapter.getBook(savedBook).isPresent());
    }

    @Test
    public void whenMultipleBooksAreAdded_ThenAllValidBooksAreReturned() {
        Book book1 = createInvalidBook();
        Book book2 = createValidBook();
        Book book3 = createValidBook();
        Book book4 = createValidBook();
        Book book5 = createInvalidBook();
        int expectedNrOfBooks = 3;

        bookManagerAdapter.addBook(book1);
        bookManagerAdapter.addBook(book2);
        bookManagerAdapter.addBook(book3);
        bookManagerAdapter.addBook(book4);
        bookManagerAdapter.addBook(book5);

        Assert.assertTrue(bookManagerAdapter.getBooks().size() == expectedNrOfBooks);
    }

    private Book createValidBook() {
        Book book = new Book();
        book.setAuthor("Isaac Asimov");
        book.setTitle("Foundation");
        return book;
    }

    private Book createInvalidBook() {
        Book book = new Book();
        book.setAuthor("Isaac_Asimov");
        book.setTitle("Foundation");
        return book;
    }
}