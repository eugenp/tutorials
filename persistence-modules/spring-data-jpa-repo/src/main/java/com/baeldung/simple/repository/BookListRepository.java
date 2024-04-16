package com.baeldung.simple.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.simple.entity.Book;

@Repository
public interface BookListRepository extends ListCrudRepository<Book, Long> {

    List<Book> findBooksByAuthor(String author);
}
