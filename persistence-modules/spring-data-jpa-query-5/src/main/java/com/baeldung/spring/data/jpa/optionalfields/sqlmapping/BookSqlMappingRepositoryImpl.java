package com.baeldung.spring.data.jpa.optionalfields.sqlmapping;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.jpa.optionalfields.BookDto;

@Repository
public class BookSqlMappingRepositoryImpl implements BookCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookDto> fetchBooks() {
        return entityManager.createNativeQuery("SELECT b.id, b.title, b.author FROM Book b", "BookMappingResultSet")
          .getResultList();
    }
}