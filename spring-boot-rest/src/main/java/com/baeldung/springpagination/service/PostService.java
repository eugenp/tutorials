package com.baeldung.springpagination.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.baeldung.springpagination.model.Post;
import com.baeldung.springpagination.repository.PostRepository;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private IUserService userService;

    @Override
    public List<Post> getPostsList(int page, int size, String sortDir, String sort) {

        PageRequest pageReq
        = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

        Page<Post> posts = postRepository.findByUser(userService.getCurrentUser(), pageReq);
        return posts.getContent();
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.getOne(id);
    }
}
