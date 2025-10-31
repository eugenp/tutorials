package com.example.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BookUnitTest {

    @Test
    void givenBook_whenUseWithEmptyContext_thenNoException() {
        Book book = new Book("The Hobbit");

        assertDoesNotThrow(() -> book.use(EmptyContext.INSTANCE));
    }
}
