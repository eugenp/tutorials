package com.baeldung.architecture.hexagonal.adapter.output;

import com.baeldung.architecture.hexagonal.db.entity.PostEntity;
import com.baeldung.architecture.hexagonal.db.jpa.PostDAO;
import com.baeldung.architecture.hexagonal.port.outbound.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostDAO postDAO;

    public PostRepositoryImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postDAO.save(postEntity);
    }
}
