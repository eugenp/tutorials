package com.example.hexagonalarchitecture.adapter;

import com.example.hexagonalarchitecture.domain.Post;
import com.example.hexagonalarchitecture.port.PostService;
import com.example.hexagonalarchitecture.port.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }

    @Override
    public Post getById(Integer id) {
        return postRepository.getById(id);
    }

    @Override
    public Post create(Post post) {
        return postRepository.create(post);
    }

    @Override
    public Post update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }
}
