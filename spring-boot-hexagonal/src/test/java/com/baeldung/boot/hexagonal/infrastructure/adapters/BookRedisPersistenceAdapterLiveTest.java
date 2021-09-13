package com.baeldung.boot.hexagonal.infrastructure.adapters;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.out.BookPersistence;
import com.baeldung.boot.hexagonal.domain.ports.service.BooksTestUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Requires a Redis instance to be up and running
 */
@Slf4j
@DisplayName("Redis Adapter Unit Tests")
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig
@SpringBootTest
class BookRedisPersistenceAdapterLiveTest {
    private static List<Book> books;

    @Autowired
    @Qualifier("bookRedisPersistenceAdapter")
    private BookPersistence persistence;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @BeforeAll
    private static void populateBooks() {
        log.info("Setting up the live tests for BookService. Populating the books... ");
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
    @DisplayName("Redis Persistence Add Book Live Test")
    @Order(1)
    void shouldSaveBook_viaRedisTemplate() {
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
    @DisplayName("Redis Persistence Get Book Live Test")
    @Order(2)
    void shouldGetBook_viaRedisRepository() {
        log.info("Checking live test for getting a book ");
        // Add a book and get the book from repository and verify it
        Book book = persistence.getBook(1L);

        assertEquals("Spring in Action", book.getTitle());
        assertEquals(1L, book.getId());
    }

    @Test
    @DisplayName("Redis Persistence Get All Books Live Test")
    @Order(3)
    @Rollback(value = false)
    void givenBooks_shouldGetAllBooks_viaJpaRepository() {
        log.info("Checking live test for getting all books");
        // Add a book (from setup) and delete the book from repository and assert that it is null
        List<Book> allBooks = persistence.getBooks();

        assertNotNull(allBooks);
        assertEquals(1, allBooks.size());
        assertEquals("Spring in Action", allBooks.get(0).getTitle());
    }

    @Test
    @DisplayName("Redis Persistence Delete Book Live Test")
    @Order(4)
    void shouldDeleteBook_viaRedisRepository() {
        log.info("Checking live test for deleting books ");
        persistence.deleteBook(1L);

        assertEquals(0, persistence.getBooks().size());
    }

    @AfterEach
    void cleanUp() {
        redisTemplate.delete("Books");
    }

}
