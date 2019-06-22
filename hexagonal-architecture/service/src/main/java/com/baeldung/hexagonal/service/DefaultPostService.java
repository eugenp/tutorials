package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.core.bo.PostBo;
import com.baeldung.hexagonal.core.ports.repository.PostRepository;
import com.baeldung.hexagonal.core.ports.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
class DefaultPostService implements PostService {

    private PostRepository postRepository;

    @Autowired
    public DefaultPostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostBo addNewPost(PostBo post) {
        return this.postRepository.save(post);
    }

    @Override
    public PostBo findPostById(String id) {
        return this.postRepository.findById(id);
    }

    @Override
    public void deletePostById(String id) {
        this.postRepository.delete(id);
    }

    @Override
    public List<PostBo> findAllPosts() {
        return this.postRepository.findAll();
    }
}
