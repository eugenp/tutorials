package org.baeldung.persistence.criteria.repository;

import java.util.List;

import org.baeldung.persistence.criteria.model.Book;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
    
}
