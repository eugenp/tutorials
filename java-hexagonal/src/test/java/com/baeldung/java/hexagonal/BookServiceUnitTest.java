package com.baeldung.java.hexagonal;

import com.baeldung.java.hexagonal.domain.ports.BookRepository;
import com.baeldung.java.hexagonal.domain.ports.BookService;
import com.baeldung.java.hexagonal.domain.model.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceUnitTest {

    public class BookTestRepository implements BookRepository {

        private List<Book> books = new ArrayList<>();

        @Override
        public Book saveBook(Book book) {
            Book savedBook = new Book()
                    .setName(book.getName())
                    .setId(String.valueOf(book.hashCode()));
            books.add(savedBook);
            return savedBook;
        }

        @Override
        public Optional<Book> findByName(String name) {
            return books.stream()
                    .filter(book -> Objects.equals(book.getName(), name))
                    .findAny();
        }
    }


    private BookService bookService = new BookService(new BookTestRepository());

    @Test
    public void whenBookAlreadyExists_thenExceptionIsThrown() {
        String bookName = "test";
        bookService.createBook(bookName);
        assertThrows(IllegalArgumentException.class, () -> bookService.createBook(bookName));
    }
}
