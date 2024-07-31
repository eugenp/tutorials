package com.baeldung.boot.jsp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.boot.jsp.dto.Book;
import com.baeldung.boot.jsp.exception.DuplicateBookException;
import com.baeldung.boot.jsp.repository.BookRepository;
import com.baeldung.boot.jsp.repository.impl.InMemoryBookRepository;
import com.baeldung.boot.jsp.repository.model.BookData;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Test
    @Order(1)
    public void givenNoAddedBooks_whenGetAllBooks_thenReturnInitialBooks() {
        Collection<Book> storedBooks = bookService.getBooks();

        assertEquals(3, storedBooks.size());
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("ISBN-TEST-1"))));
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("ISBN-TEST-2"))));
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("ISBN-TEST-3"))));
    }

    @Test
    @Order(2)
    public void givenBookNotAlreadyExists_whenAddBook_thenReturnSuccessfully() {
        Book bookToBeAdded = new Book("ISBN-ADD-TEST-4", "Added Book 4", "Added Book 4 Author");
        Book storedBook = bookService.addBook(bookToBeAdded);

        assertEquals(bookToBeAdded.getIsbn(), storedBook.getIsbn());
    }

    @Test
    @Order(3)
    public void givenBookAlreadyExists_whenAddBook_thenDuplicateBookException() {
        Book bookToBeAdded = new Book("ISBN-ADD-TEST-4", "Updated Book 4", "Updated Book 4 Author");

        assertThrows(DuplicateBookException.class, () -> bookService.addBook(bookToBeAdded));
    }

    @Configuration
    @ComponentScan("com.baeldung.boot.jsp")
    static class ContextConfiguration {

        @Bean
        public BookRepository provideBookRepository() {
            return new InMemoryBookRepository(initialBookData());
        }

        private static Map<String, BookData> initialBookData() {
            Map<String, BookData> initData = new HashMap<>();
            initData.put("ISBN-TEST-1", new BookData("ISBN-TEST-1", "Book 1", "Book 1 Author"));
            initData.put("ISBN-TEST-2", new BookData("ISBN-TEST-2", "Book 2", "Book 2 Author"));
            initData.put("ISBN-TEST-3", new BookData("ISBN-TEST-3", "Book 3", "Book 3 Author"));
            return initData;
        }
    }
}