package com.baeldung.shallowdeepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {
    @Test
    public void testDeepCopy_copyConstructor() {

        // Create an Author
        Book originalBook = new Book("Little Sally", 2020);
        Author originalAuthor = new Author("Sally McGregor", 40, originalBook);

        //Create Deep Copy
        Author copyAuthor = originalAuthor.deepCopyUsingCopyConstructor();

        //Modify Book object
        copyAuthor.getRecentBookPublished().setBookName("Sally in Paris");

        Assertions.assertNotEquals(originalAuthor.getRecentBookPublished().getBookName(),
                copyAuthor.getRecentBookPublished().getBookName());


    }

    @Test
    public void testDeepCopy_serialization() {

        // Create an Author
        Book originalBook = new Book("Little Sally", 2020);
        Author originalAuthor = new Author("Sally McGregor", 40, originalBook);

        //Create Deep Copy
        Author copyAuthor = originalAuthor.deepCopyUsingSerialization();

        //Modify Book object
        copyAuthor.getRecentBookPublished().setBookName("Sally in Paris");

        Assertions.assertNotEquals(originalAuthor.getRecentBookPublished().getBookName(),
                copyAuthor.getRecentBookPublished().getBookName());


    }
}
