package com.baeldung.springdata.mongodb;

import org.springframework.data.domain.Range;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Similarity;
import org.springframework.data.domain.Vector;
import org.springframework.data.mongodb.repository.VectorSearch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface BookRepository extends CrudRepository<Book, String> {
    @VectorSearch(indexName = "book-vector-index", numCandidates="200")
    SearchResults<Book> searchTop3ByEmbeddingNear(Vector vector,
        Similarity similarity);

    @VectorSearch(indexName = "book-vector-index", limit="10", numCandidates="200")
    SearchResults<Book> searchByEmbeddingNear(Vector vector, Score score);

    @VectorSearch(indexName = "book-vector-index", limit = "10", numCandidates="200")
    SearchResults<Book> searchByYearPublishedAndEmbeddingNear(String yearPublished, Vector vector,
        Score score);
    @VectorSearch(indexName = "book-vector-index", limit = "10", numCandidates="200")
    SearchResults<Book> searchByEmbeddingWithin(Vector vector, Range<Similarity> range);
}
