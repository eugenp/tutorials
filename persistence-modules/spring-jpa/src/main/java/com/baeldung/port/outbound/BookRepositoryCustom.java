package com.baeldung.port.outbound;

import java.util.List;

import com.baeldung.port.model.Book;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
    
}
