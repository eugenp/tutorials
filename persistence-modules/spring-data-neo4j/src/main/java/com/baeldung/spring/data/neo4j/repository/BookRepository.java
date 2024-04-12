package com.baeldung.spring.data.neo4j.repository;


import com.baeldung.spring.data.neo4j.domain.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends Neo4jRepository<Book, String> {
    Book findOneByTitle(String title);
    List<Book> findAllByYear(Integer year);
}