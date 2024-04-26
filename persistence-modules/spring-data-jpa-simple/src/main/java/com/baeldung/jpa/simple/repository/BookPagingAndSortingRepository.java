package com.baeldung.jpa.simple.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.jpa.simple.model.Book;

@Repository
public interface BookPagingAndSortingRepository extends PagingAndSortingRepository<Book, Long>, ListCrudRepository<Book, Long> {

    List<Book> findBooksByAuthor(String author, Pageable pageable);
}