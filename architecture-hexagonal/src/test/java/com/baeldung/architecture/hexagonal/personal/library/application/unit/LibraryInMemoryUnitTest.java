package com.baeldung.architecture.hexagonal.personal.library.application.unit;

import com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade.Library;
import com.baeldung.architecture.hexagonal.personal.library.application.configuration.LibraryInMemoryConfiguration;
import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class LibraryInMemoryUnitTest {

    private final Library library = LibraryInMemoryConfiguration.createLibraryInMemory();

    @BeforeEach
    void prepareLibrary() {
        library.removeAllBooks();
        library.addBook(new Book("isbn_1", "Hexagonal Architecture in Practice"));
        library.addBook(new Book("isbn_2", "The Art of Unit Tests"));
    }

    @Test
    void weCanAddBookToTheLibrary() {
        library.addBook(new Book("isbn_3", "DDD"));
        List<Book> twoBooks = library.getAllBooks();

        Assertions.assertThat(twoBooks).hasSize(3);
    }

    @Test
    void weCanRemoveBookFromTheLibrary() {
        library.removeBook("isbn_2");
        List<Book> oneBookLibrary = library.getAllBooks();

        Assertions.assertThat(oneBookLibrary).hasSize(1);
    }
}
