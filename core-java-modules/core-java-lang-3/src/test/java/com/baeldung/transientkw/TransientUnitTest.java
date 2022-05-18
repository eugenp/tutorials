package com.baeldung.transientkw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class TransientUnitTest {

    @Test
    void givenTransient_whenSerDe_thenVerifyValues() throws Exception {
        Book book = new Book();
        book.setBookName("Java Reference");
        book.setDescription("will not be saved");
        book.setCopies(25);
        
        BookSerDe.serialize(book);
        Book book2 = BookSerDe.deserialize();
        
        assertEquals("Java Reference", book2.getBookName());
        assertNull(book2.getDescription());
        assertEquals(0, book2.getCopies());
    }
    
    @Test
    void givenFinalTransient_whenSerDe_thenValuePersisted() throws Exception {
        Book book = new Book();
        
        BookSerDe.serialize(book);
        Book book2 = BookSerDe.deserialize();
        
        assertEquals("Fiction", book2.getBookCategory());     
    }
    
    @AfterAll
    public static void cleanup() {
        File file = new File(BookSerDe.fileName);
        file.deleteOnExit();
    }

}
