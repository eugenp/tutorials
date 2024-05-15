package com.baeldung.shallowcopyvsdeepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {

    @Test
    void whenShallowCopy_thenObjectsAreDifferent(){
        Publisher publisher = new Publisher("Penguin Books");
        Book originalBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", publisher);

        Book copiedBook = new Book(originalBook.getTitle(), originalBook.getAuthor(), originalBook.getPublisher());

        Assertions.assertNotEquals(originalBook, copiedBook);
    }

    @Test
    void whenCopiedObjectIsModified_thenOriginalObjectChanges(){
        Publisher publisher = new Publisher("Penguin Books");
        Book originalBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", publisher);

        Book copiedBook = new Book(originalBook.getTitle(), originalBook.getAuthor(), originalBook.getPublisher());
        copiedBook.getPublisher().setName("HarperCollins");

        Assertions.assertEquals(originalBook.getPublisher().getName(), copiedBook.getPublisher().getName());
    }
}
