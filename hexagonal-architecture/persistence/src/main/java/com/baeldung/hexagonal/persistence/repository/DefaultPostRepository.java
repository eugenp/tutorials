package com.baeldung.hexagonal.persistence.repository;

import com.baeldung.hexagonal.core.bo.PostBo;
import com.baeldung.hexagonal.core.exception.PostAlreadyExistsException;
import com.baeldung.hexagonal.core.exception.PostNotFoundException;
import com.baeldung.hexagonal.core.ports.repository.PostRepository;
import com.baeldung.hexagonal.persistence.entity.Post;
import com.baeldung.hexagonal.persistence.mapper.PostEntityMapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
class DefaultPostRepository implements PostRepository {

    private PostJpaRepository jpaRepository;
    private BoundMapperFacade<Post, PostBo> postMapper;

    @Autowired
    public DefaultPostRepository(PostJpaRepository jpaRepository, PostEntityMapper postMapper) {
        this.jpaRepository = jpaRepository;
        this.postMapper = postMapper.postMapper();
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
    public PostBo findById(String id) {
        Post post = this.jpaRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Not found for id " + id));
        return this.postMapper.map(post);
    }

    @Override
    public void delete(String id) {
        Post post = this.jpaRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Not found for id" + id));
        this.jpaRepository.delete(post);
    }

    @Override
    public List<PostBo> findAll() {
        return StreamSupport
                .stream(this.jpaRepository.findAll().spliterator(), false)
                .map(this.postMapper::map)
                .collect(toList());
    }
}