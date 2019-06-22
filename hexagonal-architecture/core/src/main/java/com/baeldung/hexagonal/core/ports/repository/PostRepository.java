package com.baeldung.hexagonal.core.ports.repository;

import com.baeldung.hexagonal.core.bo.PostBo;

import java.util.List;

public interface PostRepository {

    PostBo save(PostBo post);

    PostBo findById(String id);

    void delete(String id);

    List<PostBo> findAll();
}
