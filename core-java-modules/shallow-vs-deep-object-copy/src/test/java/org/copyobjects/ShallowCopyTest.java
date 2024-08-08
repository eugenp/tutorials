package org.copyobjects;

import org.copyobjects.shallowcopy.Author;
import org.copyobjects.shallowcopy.Book;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShallowCopyTest {
    @Test
    public void verifyShallowCopySharesObjectReferences() throws CloneNotSupportedException {
        Author john = new Author("John", "john@oracle.com");
        Book book1 = new org.copyobjects.shallowcopy.Book("Book1", 150, john);
        book1.print();

        //let us clone book2 object from book1 object
        Book book2 = book1.clone();
        book2.print();

        System.out.println("Let's change and print the cloned object");
        book2.setName("Book2");
        book2.setPageCount(200);
        book2.getAuthor().setAuthorName("Lara");
        book2.getAuthor().setEmail("lara@oracle.com");

        //lets print book2 and book1 object details
        book2.print();
        book1.print();

        assertEquals(book1.getAuthor().hashCode(), book2.getAuthor().hashCode());
    }
}