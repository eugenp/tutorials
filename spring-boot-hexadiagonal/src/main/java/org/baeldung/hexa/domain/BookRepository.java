package org.baeldung.hexa.domain;

import java.util.List;

public interface BookRepository {

    public void storeBook(Book book);
    
    public List<Book> getBooksByAuthor(String author);
    
    public List<Book> getAllBooks();
}
