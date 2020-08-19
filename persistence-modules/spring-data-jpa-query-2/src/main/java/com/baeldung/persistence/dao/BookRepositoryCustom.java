package com.baeldung.persistence.dao;

import com.baeldung.persistence.model.Book;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
    
}
