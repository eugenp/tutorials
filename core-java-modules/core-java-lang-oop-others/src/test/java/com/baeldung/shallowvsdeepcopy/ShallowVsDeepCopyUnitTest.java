package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ShallowVsDeepCopyUnitTest {

    @Test
    public void givenBook_whenShallowCopyIsChanged_thenCompareAuthorName() {
        Book book = new Book("Lorem ipsum", "0-7252-1902-5", new Author("John Doe", 28));

        Book copy = new Book(book);
        copy.getAuthor().setName("Jane Doe");
        copy.setTitle("Test");

        assertEquals(book.getAuthor().getName(), copy.getAuthor().getName());
        assertNotEquals(book.hashCode(), copy.hashCode());
        assertEquals(book.getAuthor().hashCode(), copy.getAuthor().hashCode());

    }

    @Test
    public void givenBook_whenDeepCopyIsChanged_thenCompareAuthorName() {
        Book book = new Book("Lorem ipsum", "0-7252-1902-5", new Author("John Doe", 28));

        Book copy = book.deepCopy();
        copy.getAuthor().setName("Jane Doe");

        assertNotEquals(book.getAuthor().getName(), copy.getAuthor().getName());
        assertNotEquals(book.hashCode(), copy.hashCode());
        assertNotEquals(book.getAuthor().hashCode(), copy.getAuthor().hashCode());

    }

}
