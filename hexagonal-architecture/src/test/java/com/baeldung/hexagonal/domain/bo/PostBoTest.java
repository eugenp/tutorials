package com.baeldung.hexagonal.domain.bo;

import com.baeldung.hexagonal.domain.bo.PostBo.PostState;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostBoTest {

    @Test
    public void givenPostWithContent_whenComputeWord_thenReturnNoOfWords() {
        PostBo post = getPostBo();
        assertEquals(91, post.computeWordCount());
    }

    @Test
    public void givenEmptyPost_whenComputeWord_thenReturnZero() {
        PostBo post = new PostBo();
        post.setContent("   ");
        assertEquals(0, post.computeWordCount());
    }


    @Test
    public void givenPostReadyForPublishing_whenIsReady_thenReturnTrue() {
        PostBo post = new PostBo();
        post.setState(PostState.READY);

        assertTrue(post.isReady());
    }

    @Test
    public void givenPostNotReadyForPublishing_whenIsReady_thenReturnFalse() {
        PostBo post = new PostBo();
        post.setState(PostState.DRAFT);

        assertFalse(post.isReady());
    }

    @Test
    public void givenPostDraft_whenIsDraft_thenReturnTrue() {
        PostBo post = new PostBo();
        post.setState(PostState.DRAFT);

        assertTrue(post.isDraft());
    }

    @Test
    public void givenPostNotDraft_whenIsDraft_thenReturnFalse() {
        PostBo post = new PostBo();
        post.setState(PostState.REVIEW);

        assertFalse(post.isDraft());
    }

    @Test
    public void givenPostSubmittedForReview_whenIsReadyForReview_thenReturnTrue() {
        PostBo post = new PostBo();
        post.setState(PostState.REVIEW);

        assertTrue(post.isReadyForReview());
    }

    @Test
    public void givenPostDraft_whenIsReadyForReview_thenReturnFalse() {
        PostBo post = new PostBo();
        post.setState(PostState.DRAFT);

        assertFalse(post.isReadyForReview());
    }

    private PostBo getPostBo() {
        PostBo post = new PostBo();
        post.setTitle("What is Lorem Ipsum?");
        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        return post;
    }
}
