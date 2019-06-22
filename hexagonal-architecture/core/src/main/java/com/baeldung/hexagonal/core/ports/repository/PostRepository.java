package com.baeldung.hexagonal.core.ports.repository;

import com.baeldung.hexagonal.core.domain.bo.PostBo;

import java.util.List;

public interface PostRepository {

    PostBo save(PostBo post);

    PostBo findById(Long id);

    void delete(Long id);

    List<PostBo> findAll();
}
