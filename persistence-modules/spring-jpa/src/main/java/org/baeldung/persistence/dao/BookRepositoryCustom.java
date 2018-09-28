package org.baeldung.persistence.dao;

import java.util.List;

import org.baeldung.persistence.model.Book;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
    
}
