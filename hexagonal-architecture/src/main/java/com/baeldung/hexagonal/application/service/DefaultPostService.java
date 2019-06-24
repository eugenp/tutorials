package com.baeldung.hexagonal.application.service;

import com.baeldung.hexagonal.domain.bo.PostBo;
import com.baeldung.hexagonal.domain.bo.PostBo.PostState;
import com.baeldung.hexagonal.domain.exception.InvalidPostActionException;
import com.baeldung.hexagonal.application.ports.repository.PostRepository;
import com.baeldung.hexagonal.application.ports.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        post.setState(PostState.DRAFT);
        return this.postRepository.save(post);
    }

    @Override
    public PostBo findPostById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public void submitForReview(Long id) {
        PostBo post = findPostById(id);
        if (post.isDraft()) {
            post.setState(PostState.REVIEW);
            this.postRepository.save(post);
        } else {
            throw new InvalidPostActionException("Cannot submit for review, post is not a draft");
        }
    }

    @Override
    public void review(Long id) {
        PostBo post = findPostById(id);
        if (post.isReadyForReview()) {
            post.setState(PostState.READY);
            this.postRepository.save(post);
        } else {
            throw new InvalidPostActionException("Post is not up for review");
        }
    }

    @Override
    public void publish(Long id) {
        PostBo post = findPostById(id);
        if (post.isReady()) {
            post.setState(PostState.PUBLISHED);
            this.postRepository.save(post);
        } else {
            throw new InvalidPostActionException("Invalid request, post is not ready to be published");
        }
    }
}
