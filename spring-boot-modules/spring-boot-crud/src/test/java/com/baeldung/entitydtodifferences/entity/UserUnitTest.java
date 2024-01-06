package com.baeldung.entitydtodifferences.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class UserUnitTest {

    @Test
    public void whenUserInitialized_thenInitializedCorrectly() {
        //when
        Book book1 = new Book("Book1", "Author1");
        Book book2 = new Book("Book2", "Author2");
        User user = new User("John", "Doe", "123 Main St", Arrays.asList(book1, book2));
        //then
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(2, user.getBooks()
            .size());
    }

    @Test
    public void givenUserOwningMultipleBooks_whenGetNameOfMostOwnedBook_thenComputedCorrectly() {
        //given
        Book book1 = new Book("Book1", "Author1");
        Book book2 = new Book("Book2", "Author2");
        Book book3 = new Book("Book2", "Author3");
        User user = new User("John", "Doe", "123 Main St", Arrays.asList(book1, book2, book3));
        //when
        String mostOwnedBook = user.getNameOfMostOwnedBook();
        //then
        assertEquals("Book2", mostOwnedBook);
    }

    @Test
    public void givenUserWithNoBooks_henGetNameOfMostOwnedBook_thenReturnedNull() {
        //given
        User user = new User("John", "Doe", "123 Main St", Collections.emptyList());
        //when
        String mostOwnedBook = user.getNameOfMostOwnedBook();
        //then
        assertNull(mostOwnedBook);
    }
}
