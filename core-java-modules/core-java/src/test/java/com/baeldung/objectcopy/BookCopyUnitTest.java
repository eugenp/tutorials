package com.baeldung.objectcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookCopyUnitTest {

    @Test
    void givenShallowCopyBookConstructor_whenPriceChanges_thenClonePriceChanges() {
        //given
        BookMetadata bookMetadata = new BookMetadata(100, "Baeldung", 2020, 100);
        ShallowCopyBook book = new ShallowCopyBook("Shallow Copy vs Deep Copy in Java", "Matei Cernăianu", "test content", bookMetadata);
        ShallowCopyBook bookCopy = new ShallowCopyBook(book);

        //when - price change
        book.getBookMetadata()
            .setPrice(150);

        //then - both object prices have been updated
        Assertions.assertNotNull(book);
        Assertions.assertNotNull(bookCopy);
        Assertions.assertEquals(book.getBookMetadata()
            .getPrice(), bookCopy.getBookMetadata()
            .getPrice());
    }

    @Test
    void givenDeepCopyBookConstructor_whenPriceChanges_thenClonePriceUnchanged() {
        //given
        BookMetadata bookMetadata = new BookMetadata(100, "Baeldung", 2020, 100);
        DeepCopyBook book = new DeepCopyBook("Shallow Copy vs Deep Copy in Java", "Matei Cernăianu", "test content", bookMetadata);
        DeepCopyBook bookCopy = new DeepCopyBook(book);

        //when - price change
        book.getBookMetadata()
            .setPrice(150);

        //then - copy object price remains unchanged
        Assertions.assertNotNull(book);
        Assertions.assertNotNull(bookCopy);
        Assertions.assertEquals(book.getBookMetadata()
            .getPrice(), 150);
        Assertions.assertEquals(bookCopy.getBookMetadata()
            .getPrice(), 100);
    }

    @Test
    void givenShallowCopyBookClone_whenPriceChanges_thenClonePriceChanges() {
        //given
        BookMetadata bookMetadata = new BookMetadata(100, "Baeldung", 2020, 100);
        ShallowCopyBook book = new ShallowCopyBook("Shallow Copy vs Deep Copy in Java", "Matei Cernăianu", "test content", bookMetadata);
        ShallowCopyBook bookCopy = book.clone();

        //when - price change
        book.getBookMetadata()
            .setPrice(150);

        //then - both object prices have been updated
        Assertions.assertNotNull(book);
        Assertions.assertNotNull(bookCopy);
        Assertions.assertEquals(book.getBookMetadata()
            .getPrice(), bookCopy.getBookMetadata()
            .getPrice());
    }

    @Test
    void givenDeepCopyBookClone_whenPriceChanges_thenClonePriceUnchanged() {
        //given
        BookMetadata bookMetadata = new BookMetadata(100, "Baeldung", 2020, 100);
        DeepCopyBook book = new DeepCopyBook("Shallow Copy vs Deep Copy in Java", "Matei Cernăianu", "test content", bookMetadata);
        DeepCopyBook bookCopy = book.clone();

        //when - price change
        book.getBookMetadata()
            .setPrice(150);

        //then - copy object price remains unchanged
        Assertions.assertNotNull(book);
        Assertions.assertNotNull(bookCopy);
        Assertions.assertEquals(book.getBookMetadata()
            .getPrice(), 150);
        Assertions.assertEquals(bookCopy.getBookMetadata()
            .getPrice(), 100);
    }
}
