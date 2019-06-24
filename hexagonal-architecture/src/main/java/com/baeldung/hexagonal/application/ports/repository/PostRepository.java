package com.baeldung.hexagonal.application.ports.repository;

import com.baeldung.hexagonal.domain.bo.PostBo;

public interface PostRepository {

    PostBo save(PostBo post);

    PostBo findById(Long id);
}
