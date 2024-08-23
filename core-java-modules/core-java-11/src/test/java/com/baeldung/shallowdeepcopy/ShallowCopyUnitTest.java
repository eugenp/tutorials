package com.baeldung.shallowdeepcopy;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ShallowCopyUnitTest {
    @Test
    public void testShallowCopy_primitives() {
        int[] original = {10,20,30};
        int[] copy = Arrays.copyOf(original, 3);
        copy[2] = 50;
        Assertions.assertNotEquals(original, copy);
    }

    @Test
    public void testShallowCopy_objects() {
        // Create an Author
        Book originalBook = new Book("Little Sally", 2020);
        Author originalAuthor = new Author("Sally McGregor", 40, originalBook);

        // Create a shallowcopy of an Author
        Author copyAuthor = originalAuthor.shallowCopy();

        // Modify the Book object in the copy
        copyAuthor.getRecentBookPublished().setBookName("Sally in Paris");
        Assertions.assertEquals(originalAuthor.getRecentBookPublished().getBookName(),
                copyAuthor.getRecentBookPublished().getBookName());
        // Since Book is shallow copied, affects both original and copy

        // Modify the Author name (Note Name is a String Immutable)
        copyAuthor.setName("Sally Li");
        Assertions.assertNotEquals(originalAuthor.getName(), copyAuthor.getName());
        // Since "name" is Immutable references different objects

    }
}
