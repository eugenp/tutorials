package com.baeldung.microstream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BooksManagerUnitTest {

    private static final Author author = new Author("Joanne", "Rowling");
    private static final Book bookOne = new Book("Harry Potter and the Philosopher's Stone", author, 1997);
    private static final Book bookTwo = new Book("Harry Potter and the Chamber of Secrets", author, 1998);

    @Test
    void givenStorageWithCustomTypeAsRoot_whenStoringAdditionalObjects_thenAdditionalObjectsAreSuccessfullyStored() {
        List<Book> books = BooksManager.storeBooks("baeldung-books", Arrays.asList(bookOne, bookTwo));

        assertThat(books).hasSize(2);
        assertThat(books.get(0)).isEqualTo(bookOne);
        assertThat(books.get(1)).isEqualTo(bookTwo);
    }

}
