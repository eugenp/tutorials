package com.baeldung.spring.data.jpa.listrepositories.lastrecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void givenPosts_whenUsingFindFirstDerivedQuery_thenReturnLastPost() {
        Post post = postRepository.findFirstByOrderByPublicationDateDesc();

        assertNotNull(post);
        assertEquals(5, post.getId());
    }

    @Test
    void givenPosts_whenUsingFindTopDerivedQuery_thenReturnLastPost() {
        Post post = postRepository.findTopByOrderByPublicationDateDesc();

        assertNotNull(post);
        assertEquals(5, post.getId());
    }

    @Test
    void givenPosts_whenUsingQueryAnnotation_thenReturnLastPost() {
        Post post = postRepository.findLastPost();

        assertNotNull(post);
        assertEquals(5, post.getId());
    }

}
