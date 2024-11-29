package com.baeldung.books.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.baeldung.books.models.Book;
import com.baeldung.books.projections.CustomBook;

@RepositoryRestResource(excerptProjection = CustomBook.class)
public interface BookRepository extends CrudRepository<Book, Long> {
}
