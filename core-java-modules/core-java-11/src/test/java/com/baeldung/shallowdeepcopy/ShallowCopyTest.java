package com.baeldung.shallowdeepcopy;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShallowCopyTest {
    @Test
    public void testPrimitives() {
        int i = 20;
        int j = i;
        j = 10;
        System.out.println("Print i: " + i); // Prints 20
        System.out.println("Print j: " + j); // Prints 10
        Assertions.assertNotEquals(i, j);
    }

    @Test
    public void testObjects() {
        // Create an Author
        Book originalBook = new Book("Little Sally", 2020);
        Author originalAuthor = new Author("Sally McGregor", 40, originalBook);

        // Create a shallowcopy of an Author
        Author copyAuthor = originalAuthor.shallowCopy();

        // Modify the Book object in the copy
        System.out.println("Original Author:" + originalAuthor);
        copyAuthor.getRecentBookPublished().setBookName("Sally in Paris");
        System.out.println("Original Author after modification: " + originalAuthor);
        System.out.println("Copy Author: " + copyAuthor);
        Assertions.assertEquals(originalAuthor.getRecentBookPublished().getBookName(),
                copyAuthor.getRecentBookPublished().getBookName());
        // Since Book is shallow copied, affects both original and copy

        // Modify the Author name (Note Name is a String Immutable)
        copyAuthor.setName("Sally Li");
        System.out.println("Original Author after modification: " + originalAuthor);
        System.out.println("Copy Author: " + copyAuthor);
        Assertions.assertNotEquals(originalAuthor.getName(), copyAuthor.getName());
        // Since "name" is Immutable references different objects

    }
}
