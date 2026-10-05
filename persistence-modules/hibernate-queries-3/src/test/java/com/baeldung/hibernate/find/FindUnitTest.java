package com.baeldung.hibernate.find;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.exceptions.EmptyResultException;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FindUnitTest {

    private static SessionFactory sessionFactory;
    private StatelessSession session;

    @BeforeAll
    static void createSession() {
        sessionFactory = new Configuration()
            .addAnnotatedClass(Author.class)
            .addAnnotatedClass(Book.class)
            .configure("find/hibernate.find.cfg.xml")
            .buildSessionFactory();
    }

    @BeforeEach
    void before() {
        session = sessionFactory.openStatelessSession();
    }

    @AfterEach
    void after() {
        session.close();
    }

    @Test
    void whenListingAllBooks_thenAllBooksAreReturned() {
        BookRepository_ repository = new BookRepository_(session);
        List<Book> books = repository.getAllBooks();

        assertEquals(7, books.size());
    }

    @Test
    void whenGettingASingleBook_thenThebookIsReturned() {
        BookRepository_ repository = new BookRepository_(session);
        Book book = repository.getBookWithTitle("Animal Farm");

        assertEquals("Animal Farm", book.getTitle());
        assertEquals(102L, book.getBookId());
    }

    @Test
    void whenGettingAnUnknownBook_thenNothingIsReturned() {
        BookRepository_ repository = new BookRepository_(session);

        assertThrows(EmptyResultException.class, () -> repository.getBookWithTitle("Unknown Book"));

        assertEquals(Optional.empty(), repository.getOptionalBookWithTitle("Unknown Book"));
        assertNull(repository.getNullableBookWithTitle("Unknown Book"));
    }

    @Test
    void whenListingBooksByAuthor_thenAllBooksAreReturned() {
        BookRepository_ repository = new BookRepository_(session);
        List<Book> books = repository.getAllBooksByAuthorName("George Orwell");

        assertEquals(2, books.size());
    }

    @Test
    void whenListingBooksInOrder_thenTheCorrectBooksAreReturned() {
        BookRepository_ repository = new BookRepository_(session);
        List<Book> books = repository.getAllBooks(Order.by(
            Sort.asc("title"),
            Sort.desc("author.name")
        ));

        assertEquals(7, books.size());
        assertEquals(101L, books.get(0).getBookId());
        assertEquals(107L, books.get(6).getBookId());
    }

    @Test
    void whenListingPagesOfBooks_thenTheCorrectBooksAreReturned() {
        BookRepository_ repository = new BookRepository_(session);
        Page<Book> books = repository.getBooksPage(PageRequest.ofPage(1, 3, true));

        assertEquals(3, books.content().size());
        assertEquals(101L, books.content().get(0).getBookId());
        assertEquals(102L, books.content().get(2).getBookId());

        assertEquals(7L, books.totalElements());
        assertTrue(books.hasNext());
        assertFalse(books.hasPrevious());
    }

}
