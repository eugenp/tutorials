package com.baeldung.hexagonal.application.service;

import com.baeldung.hexagonal.domain.bo.PostBo;
import com.baeldung.hexagonal.domain.bo.PostBo.PostState;
import com.baeldung.hexagonal.domain.exception.InvalidPostActionException;
import com.baeldung.hexagonal.domain.exception.PostAlreadyExistsException;
import com.baeldung.hexagonal.domain.exception.PostNotFoundException;
import com.baeldung.hexagonal.application.ports.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    DefaultPostService postService;

    @Test
    public void whenAddNewPost_thenCreateAndReturnAPost() {
        PostBo newPost = new PostBo();
        newPost.setTitle("Test");

        PostBo savedPost = new PostBo();
        savedPost.setId(1001L);
        savedPost.setTitle("Test");
        savedPost.setState(PostState.DRAFT);

        when(postRepository.save(newPost)).thenReturn(savedPost);
        PostBo actual = postService.addNewPost(newPost);

        assertEquals(1001L, actual.getId().longValue());
        assertEquals(PostState.DRAFT, actual.getState());
    }

    @Test(expected = PostAlreadyExistsException.class)
    public void whenAddDuplicatePost_thenThrowPostAlreadyExistsException() {

        when(postRepository.save(any(PostBo.class))).thenThrow(PostAlreadyExistsException.class);
        postService.addNewPost(new PostBo());
    }

    @Test
    public void whenFindPostById_thenReturnRelevantPost() {
        Long id = 1001L;
        PostBo post = new PostBo();
        post.setId(id);

        when(postRepository.findById(id)).thenReturn(post);
        PostBo actual = postService.findPostById(id);

        assertEquals(id, actual.getId());
    }

    @Test(expected = PostNotFoundException.class)
    public void whenFindPostById_thenThrowPostNotFoundException() {
        when(postRepository.findById(anyLong())).thenThrow(PostNotFoundException.class);
        postService.findPostById(-1L);
    }

    @Test
    public void whenSubmitPostForReview_thenMovePostToReview() {
        Long id = 1001L;
        PostBo savedPost = new PostBo();
        savedPost.setId(id);
        savedPost.setState(PostState.DRAFT);

        PostBo updatedPost = new PostBo();
        updatedPost.setId(1001L);
        updatedPost.setState(PostState.REVIEW);

        when(postRepository.findById(id)).thenReturn(savedPost);
        when(postRepository.save(any(PostBo.class))).thenReturn(updatedPost);
        postService.submitForReview(id);

        assertEquals(id.longValue(), updatedPost.getId().longValue());
        assertEquals(PostState.REVIEW, updatedPost.getState());
    }

    @Test(expected = InvalidPostActionException.class)
    public void whenSubmitPublishedPostForReview_thenThrowInvalidPostActionException() {
        Long id = 1001L;
        PostBo savedPost = new PostBo();
        savedPost.setId(id);
        savedPost.setState(PostState.PUBLISHED);

        when(postRepository.findById(anyLong())).thenReturn(savedPost);
        postService.submitForReview(id);
    }

    @Test
    public void whenSubmitReview_thenMoveToReady() {
        Long id = 1001L;
        PostBo savedPost = new PostBo();
        savedPost.setId(id);
        savedPost.setState(PostState.REVIEW);

        PostBo updatedPost = new PostBo();
        updatedPost.setId(1001L);
        updatedPost.setState(PostState.READY);

        when(postRepository.findById(id)).thenReturn(savedPost);
        when(postRepository.save(any(PostBo.class))).thenReturn(updatedPost);
        postService.review(id);

        assertEquals(id.longValue(), updatedPost.getId().longValue());
        assertEquals(PostState.READY, updatedPost.getState());
    }

    @Test(expected = InvalidPostActionException.class)
    public void whenSubmitReviewForDraft_thenThrowInvalidPostActionException() {
        Long id = 1001L;
        PostBo savedPost = new PostBo();
        savedPost.setId(id);
        savedPost.setState(PostState.DRAFT);

        when(postRepository.findById(anyLong())).thenReturn(savedPost);
        postService.review(id);
    }

    @Test
    public void whenPublishPost_thenMoveToPublished() {
        Long id = 1001L;
        PostBo savedPost = new PostBo();
        savedPost.setId(id);
        savedPost.setState(PostState.READY);

        PostBo updatedPost = new PostBo();
        updatedPost.setId(1001L);
        updatedPost.setState(PostState.PUBLISHED);

        when(postRepository.findById(id)).thenReturn(savedPost);
        when(postRepository.save(any(PostBo.class))).thenReturn(updatedPost);
        postService.publish(id);

        assertEquals(id.longValue(), updatedPost.getId().longValue());
        assertEquals(PostState.PUBLISHED, updatedPost.getState());
    }

    @Test(expected = InvalidPostActionException.class)
    public void whenPublishUnReviewedPost_thenThrowInvalidPostActionException() {
        Long id = 1001L;
        PostBo savedPost = new PostBo();
        savedPost.setId(id);
        savedPost.setState(PostState.REVIEW);

        when(postRepository.findById(anyLong())).thenReturn(savedPost);
        postService.publish(id);
    }
}
