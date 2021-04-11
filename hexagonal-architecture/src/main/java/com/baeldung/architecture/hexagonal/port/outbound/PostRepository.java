package com.baeldung.architecture.hexagonal.port.outbound;

import com.baeldung.architecture.hexagonal.db.entity.PostEntity;

public interface PostRepository {

    PostEntity save(final PostEntity postEntity);

}
