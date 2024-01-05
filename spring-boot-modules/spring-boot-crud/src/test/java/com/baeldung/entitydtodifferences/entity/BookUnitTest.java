package com.baeldung.entitydtodifferences.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

public class BookUnitTest {

    @Test
    public void whenBookInitialized_thenInitializedCorrectly() {
        //when
        Book book = new Book("Book1", "Author1");
        //then
        assertNotNull(book);
        assertEquals("Book1", book.getName());
        assertEquals("Author1", book.getAuthor());
    }
}
