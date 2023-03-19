package com.baeldung.spring.data.jpa.listrepositories.repository;

import com.baeldung.spring.data.jpa.listrepositories.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookPagingAndSortingRepository extends PagingAndSortingRepository<Book, Long>, ListCrudRepository<Book, Long> {

    List<Book> findBooksByAuthor(String author, Pageable pageable);
}