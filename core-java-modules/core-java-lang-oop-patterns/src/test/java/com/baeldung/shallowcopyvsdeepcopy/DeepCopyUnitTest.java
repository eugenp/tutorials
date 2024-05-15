package com.baeldung.shallowcopyvsdeepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    void whenDeepCopy_thenObjectsAreNotSame(){
        Publisher publisher = new Publisher("Penguin Books");
        Book originalBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", publisher);

        Book copiedBook = new Book(originalBook);

        Assertions.assertNotEquals(originalBook, copiedBook);
    }

    @Test
    void whenCopiedObjectIsModified_thenOriginalObjectDoesNotChange(){
        Publisher publisher = new Publisher("Penguin Books");
        Book originalBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", publisher);

        Book copiedBook = new Book(originalBook);
        copiedBook.getPublisher().setName("HarperCollins");

        Assertions.assertNotEquals(originalBook.getPublisher().getName(), copiedBook.getPublisher().getName());
    }
}
