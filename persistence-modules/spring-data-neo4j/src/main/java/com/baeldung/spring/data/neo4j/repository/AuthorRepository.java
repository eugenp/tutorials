package com.baeldung.spring.data.neo4j.repository;

import com.baeldung.spring.data.neo4j.domain.Author;
import com.baeldung.spring.data.neo4j.domain.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends Neo4jRepository<Author, Long> {
    @Query("MATCH (b:Book)-[:WRITTEN_BY]->(a:Author) WHERE a.name = $name AND b.year > $year RETURN b")
    List<Book> findBooksAfterYear(@Param("name") String name, @Param("year") Integer year);
}
