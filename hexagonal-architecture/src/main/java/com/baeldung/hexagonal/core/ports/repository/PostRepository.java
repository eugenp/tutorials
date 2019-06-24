package com.baeldung.hexagonal.core.ports.repository;

import com.baeldung.hexagonal.core.domain.bo.PostBo;

public interface PostRepository {

    PostBo save(PostBo post);

    PostBo findById(Long id);
}
