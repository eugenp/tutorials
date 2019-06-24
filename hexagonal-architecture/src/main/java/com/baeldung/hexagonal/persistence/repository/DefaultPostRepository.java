package com.baeldung.hexagonal.persistence.repository;

import com.baeldung.hexagonal.domain.bo.PostBo;
import com.baeldung.hexagonal.domain.exception.PostAlreadyExistsException;
import com.baeldung.hexagonal.domain.exception.PostNotFoundException;
import com.baeldung.hexagonal.application.ports.repository.PostRepository;
import com.baeldung.hexagonal.persistence.entity.Post;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class DefaultPostRepository implements PostRepository {

    private PostJpaRepository jpaRepository;
    private BoundMapperFacade<Post, PostBo> postMapper;

    @Autowired
    public DefaultPostRepository(PostJpaRepository jpaRepository,
                                 @Qualifier("postEntityMapper") BoundMapperFacade<Post, PostBo> postMapper) {
        this.jpaRepository = jpaRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostBo save(PostBo post) {
        Post entity;
        try {
            entity = this.jpaRepository.save(this.postMapper.mapReverse(post));

        } catch (DataIntegrityViolationException e) {
            log.error("Duplicate post title", e);
            throw new PostAlreadyExistsException("Post already exists with title " + post.getTitle());
        }
        return this.postMapper.map(entity);
    }

    @Override
    public PostBo findById(Long id) {
        Post post = this.jpaRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Not found for id " + id));
        return this.postMapper.map(post);
    }
}