package com.baeldung.persistence.dao;

import java.util.List;

import com.baeldung.persistence.model.Book;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
    
}
