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

}