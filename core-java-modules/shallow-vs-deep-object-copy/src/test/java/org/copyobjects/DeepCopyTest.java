package org.copyobjects;

import org.copyobjects.deepcopy.Author;
import org.copyobjects.deepcopy.Book;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeepCopyTest {
    @Test
    public void whenDeepCloned_thenObjectReferencesAreNotSharedWithOriginalObject() throws CloneNotSupportedException{
        Author john = new Author("John", "john@oracle.com");
        Book book = new Book("Book", 150, john);
        
        Book clonedBook = book.clone();
        book2.setName("Cloned Book");
        book2.setPageCount(200);
        book2.getAuthor().setAuthorName("Lara"):
        book2.getAuthor().setEmail("lara@oracle.com");

        assertNotEquals(book.getAuthor(), clonedBook.getAuthor());
        assertNotEquals(book.getAuthor().hashCode(), clonedBook.getAuthor().hashCode());
    }
}
