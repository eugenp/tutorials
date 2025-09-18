package com.baeldung.springdata.pgvector;

import java.util.List;

import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Vector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("documentRepository")
public interface BookRepository extends JpaRepository<Book, String> {
    SearchResults<Book> searchByYearPublishedAndEmbeddingNear(String yearPublished, Vector vector, Score scoreThreshold);

    List<Book> searchTop3ByYearPublished(String yearPublished);
}