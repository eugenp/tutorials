package com.baeldung.repository;

import com.baeldung.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends MongoRepository<Author, UUID> {

    Optional<Author> findByEmail(String email);

    List<Author> findByActiveTrueAndArticleCountGreaterThanEqual(int articleCount);

    @Query("{ 'article_count': { $gte: ?0, $lte: ?1 }, 'active': true }")
    List<Author> findActiveAuthorsInArticleRange(int minArticles, int maxArticles);

    @Query(value = "{ 'active': true }", fields = "{ 'email': 1 }")
    List<Author> findActiveAuthorEmails();

}