package com.baeldung.architecture.hexagonal.personal.library.application.integration;

import com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade.Library;
import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LibraryInMemoryConfiguredWithSpringIntegrationTest {

    private final Library library;

    @Autowired
    LibraryInMemoryConfiguredWithSpringIntegrationTest(Library library) {
        this.library = library;
    }

    @BeforeEach
    void prepareLibrary() {
        library.removeAllBooks();
        library.addBook(new Book("isbn_1", "Hexagonal Architecture in Practice"));
        library.addBook(new Book("isbn_2", "The Art of Unit Tests"));
    }

    @Test
    void weCanAddBookToLibrary() {
        // given
        Book book = new Book("isbn_3", "DDD");

        // when
        library.addBook(book);

        // then
        List<Book> allBooks = library.getAllBooks();

        Assertions.assertThat(allBooks).hasSize(3);
    }

    @Test
    void weCanRemoveBookFromLibrary() {
        library.removeBook("isbn_2");

        List<Book> oneBookLibrary = library.getAllBooks();

        Assertions.assertThat(oneBookLibrary).hasSize(1);
    }
}
