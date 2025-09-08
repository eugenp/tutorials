package com.baeldung.springdata.vector;

import java.util.List;

import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Vector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("documentRepository")
public interface DocumentRepository extends JpaRepository<Document, String> {
    // Vector similarity search using pgvector operator
/*
    @Query("SELECT id, content, the_embedding FROM documents ORDER BY the_embedding <-> :theEmbedding LIMIT 3")
    List<Document> findNearest(@Param("theEmbedding") Vector embedding);*/


    SearchResults<Document> searchTop3ByYearPublishedAndTheEmbeddingNear(String yearPublished, Vector vector,
        Score scoreThreshold);


    List<Document> searchTop3ByYearPublished(String yearPublished);

/*
    SearchResults<Document> searchByYearPublishedAndTheEmbeddingWithin(String yearPublished, Vector theEmbedding,
        Range<Similarity> range, Limit topK);*/
}