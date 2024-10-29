package com.baeldung.spring.data.jpa.optionalfields.sqlmapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.jpa.optionalfields.Book;

@Repository
public interface BookSqlMappingRepository extends JpaRepository<Book, Integer>, BookCustomRepository {
}
