package com.baeldung.hexagonal.persistence.repository;

import com.baeldung.hexagonal.domain.bo.PostBo;
import com.baeldung.hexagonal.domain.bo.PostBo.PostState;
import com.baeldung.hexagonal.domain.exception.PostAlreadyExistsException;
import com.baeldung.hexagonal.domain.exception.PostNotFoundException;
import com.baeldung.hexagonal.persistence.entity.Post;
import ma.glasnost.orika.BoundMapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPostRepositoryTest {

    @InjectMocks
    DefaultPostRepository postRepository;

    @Mock
    BoundMapperFacade<Post, PostBo> postMapper;

    @Mock
    PostJpaRepository jpaRepository;

    @Test
    public void whenSave_thenCreateAndReturnAPost() {
        PostBo newPost = new PostBo();
        newPost.setTitle("Test");

        Post newPostEntity = new Post();
        newPostEntity.setTitle(newPost.getTitle());

        PostBo savedPost = new PostBo();
        savedPost.setId(1001L);
        savedPost.setTitle("Test");
        savedPost.setState(PostState.DRAFT);

        Post savedPostEntity = new Post();
        savedPostEntity.setId(savedPost.getId());
        savedPostEntity.setTitle(savedPost.getTitle());
        savedPostEntity.setState(savedPost.getState());

        when(postMapper.mapReverse(any(PostBo.class))).thenReturn(newPostEntity);
        when(jpaRepository.save(any(Post.class))).thenReturn(savedPostEntity);
        when(postMapper.map(any(Post.class))).thenReturn(savedPost);
        PostBo actual = postRepository.save(newPost);

        assertEquals(1001L, actual.getId().longValue());
        assertEquals(PostState.DRAFT, actual.getState());
    }

    @Test(expected = PostAlreadyExistsException.class)
    public void whenSaveDuplicatePost_thenThrowPostAlreadyExistsException() {

        when(postMapper.mapReverse(any(PostBo.class))).thenReturn(new Post());
        when(jpaRepository.save(any(Post.class))).thenThrow(PostAlreadyExistsException.class);
        postRepository.save(new PostBo());
    }

    @Test
    public void whenFindById_thenReturnRelevantPost() {
        Long id = 1001L;

        PostBo post = new PostBo();
        post.setId(id);

        Post postEntity = new Post();
        post.setId(post.getId());

        when(jpaRepository.findById(id)).thenReturn(Optional.of(postEntity));
        when(postMapper.map(any(Post.class))).thenReturn(post);
        PostBo actual = postRepository.findById(id);

        assertEquals(id, actual.getId());
    }

    @Test(expected = PostNotFoundException.class)
    public void whenFindById_thenThrowPostNotFoundException() {
        when(jpaRepository.findById(anyLong())).thenThrow(PostNotFoundException.class);
        postRepository.findById(-1L);
    }
}
