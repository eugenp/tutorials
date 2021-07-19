package com.baeldung.halbrowser.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.baeldung.halbrowser.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

  @RestResource(rel = "title-contains", path="title-contains")
  Page<Book> findByTitleContaining(@Param("query") String query, Pageable page);

  @RestResource(rel = "author-contains", path="author-contains", exported = false)
  Page<Book> findByAuthorContaining(@Param("query") String query, Pageable page);

}
