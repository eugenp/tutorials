package com.baeldung.springdata.pgvector;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Similarity;
import org.springframework.data.domain.Vector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgvectorBookRepository")
public interface PGvectorBookRepository extends JpaRepository<Book, String> {
    SearchResults<Book> searchByYearPublishedAndEmbeddingNear(String yearPublished, Vector vector, Score scoreThreshold);

    SearchResults<Book> searchByYearPublishedAndEmbeddingWithin(String yearPublished, Vector vector, Range<Similarity> range, Limit topK);

}