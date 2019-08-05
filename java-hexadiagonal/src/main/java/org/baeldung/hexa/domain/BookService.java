package org.baeldung.hexa.domain;

import java.util.List;

public interface BookService {

    public void storeBook(Book book);

    public List<Book> getBooksByAuthor(String author);
    
    public int getNumberOfBooks();
    
    public int getNumberOfAuthors();
    
}
