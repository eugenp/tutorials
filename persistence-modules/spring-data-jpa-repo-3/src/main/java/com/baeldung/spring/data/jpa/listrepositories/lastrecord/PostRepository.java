package com.baeldung.spring.data.jpa.listrepositories.lastrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findFirstByOrderByPublicationDateDesc();

    Post findTopByOrderByPublicationDateDesc();

    @Query("SELECT p FROM Post p ORDER BY p.publicationDate DESC LIMIT 1")
    Post findLastPost();

}
