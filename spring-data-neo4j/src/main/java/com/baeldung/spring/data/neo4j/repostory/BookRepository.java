package com.baeldung.spring.data.neo4j.repostory;

import com.baeldung.spring.data.neo4j.model.Book;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface BookRepository extends GraphRepository<Book> {
    Book findByTitle(@Param("title") String title);

    @Query("MATCH (m:Book) WHERE m.title =~ ('(?i).*'+{title}+'.*') RETURN m")
    Collection<Book> findByTitleContaining(@Param("title") String title);

    @Query("MATCH (m:Book)<-[:ACTED_IN]-(a:Person) RETURN m.title as Book, collect(a.name) as cast LIMIT {limit}")
    List<Map<String,Object>> graph(@Param("limit") int limit);
}