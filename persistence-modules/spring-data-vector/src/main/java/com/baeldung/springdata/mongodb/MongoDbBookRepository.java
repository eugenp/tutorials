package com.baeldung.springdata.mongodb;

import org.springframework.data.domain.Range;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Similarity;
import org.springframework.data.domain.Vector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.VectorSearch;
import org.springframework.stereotype.Repository;

@Repository("mongoDbBookRepository")
public interface MongoDbBookRepository extends MongoRepository<Book, String> {
    @VectorSearch(indexName = "book-vector-index", limit = "10", numCandidates="200")
    SearchResults<Book> searchByYearPublishedAndEmbeddingNear(String yearPublished, Vector vector, Score score);

    @VectorSearch(indexName = "book-vector-index", limit = "10", numCandidates="200")
    SearchResults<Book> searchByYearPublishedAndEmbeddingWithin(String yearPublished, Vector vector, Range<Similarity> range);
}
