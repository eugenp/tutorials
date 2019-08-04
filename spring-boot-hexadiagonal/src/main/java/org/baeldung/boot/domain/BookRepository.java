package org.baeldung.boot.domain;

import java.util.List;

public interface BookRepository {

    public void storeBook(Book book);
    
    public List<Book> getBooksByAuthor(String author);
    
}
