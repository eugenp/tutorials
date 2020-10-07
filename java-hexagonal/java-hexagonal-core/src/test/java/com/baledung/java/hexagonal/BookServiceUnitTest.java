package com.baledung.java.hexagonal;

import com.baeldung.java.hexagonal.model.Book;
import com.baeldung.java.hexagonal.ports.inbound.BookRepository;
import com.baeldung.java.hexagonal.ports.inbound.BookService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceUnitTest {

    private BookService bookService = new BookService(new InMemoryTestRepository());

@Test
public void whenBookAlreadyExists_thenExceptionIsThrown() {
    String bookName = "test";
    bookService.createBook(bookName);
    assertThrows(IllegalArgumentException.class, () -> bookService.createBook(bookName));
}

    public class InMemoryTestRepository implements BookRepository {

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
}
