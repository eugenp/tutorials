package com.baeldung.boot.hexagonal.infrastructure.adapters;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.out.BookPersistence;
import com.baeldung.boot.hexagonal.domain.ports.service.BooksTestUtility;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.MappingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Requires H2 database to be up and running
 */

@Slf4j
@DisplayName("JPA Adapter Live Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@SpringBootTest
@SpringJUnitConfig
@TestPropertySource(locations = { "classpath:application-test.properties" })
class BookJpaPersistenceAdapterLiveTest {

    private static List<Book> books;

    @Autowired
    @Qualifier("bookJpaPersistenceAdapter")
    private BookPersistence persistence;

    @BeforeAll
    private static void populateBooks() {
        log.info("Setting up the live tests for BookService. Populating the books...");
        books = BooksTestUtility.populateBooks();
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Book book = books.get(0);
        book.setId(1L);
        persistence.addBook(book);
    }

    @Test()
    @DisplayName("JPA Persistence Add Book Live Test")
    @Order(1)
    @Rollback(value = false)
    void shouldSaveBook_viaJpaRepository() {
        log.info("Checking live test for creating a book ");
        // Add a book and get the book from repository and verify it
        Book book = books.get(1);
        book.setId(2L);
        persistence.addBook(book);
        book = persistence.getBook(2L);

        assertEquals("Effective Java", book.getTitle());
        assertEquals(2L, book.getId());
    }

    @Test
    @DisplayName("JPA Persistence Get Book Live Test")
    @Order(2)
    @Rollback(value = false)
    void shouldGetBook_viaJpaRepository() {
        log.info("Checking live test for getting a book ");
        // Add a book and get the book from repository and verify it
        Book book = persistence.getBook(1L);

        assertEquals("Spring in Action", book.getTitle());
        assertEquals(1L, book.getId());
    }

    @Test
    @DisplayName("JPA Persistence Get All Books Live Test")
    @Order(3)
    @Rollback(value = false)
    void givenBooks_shouldGetAllBooks_viaJpaRepository() {
        log.info("Checking live test for getting all books");
        // Add a book (from setup) and delete the book from repository and assert that it is null
        List<Book> allBooks = persistence.getBooks();

        assertEquals(2, allBooks.size());
    }

    @Test()
    @DisplayName("JPA Persistence Update Book Live Test")
    @Order(4)
    @Rollback(value = false)
    void shouldUpdateBook_viaJpaRepository() {
        log.info("Checking live test for creating a book ");
        // Add a book and get the book from repository and verify it
        Book book = books.get(2);
        book.setId(3L);
        persistence.addBook(book);
        book = persistence.getBook(3L);

        assertEquals("Spring Microservices in Action", book.getTitle());
        assertEquals(3L, book.getId());

        book.setTitle("Spring Microservices in Action Updated");
        persistence.updateBook(book);
        assertEquals("Spring Microservices in Action Updated", book.getTitle());
    }

    @Test
    @DisplayName("JPA Persistence Delete Book Live Test")
    @Order(5)
    void shouldDeleteBook_viaJpaRepository() {
        log.info("Checking live test for getting a book ");
        // Add a book (from setup) and delete the book from repository and assert that it throws an exception
        persistence.deleteBook(1L);

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            persistence.getBook(1L);
        });
    }

}
