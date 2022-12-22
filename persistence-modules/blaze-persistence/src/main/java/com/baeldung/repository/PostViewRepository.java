package com.baeldung.repository;

import com.baeldung.view.PostWithAuthorView;
import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PostViewRepository extends EntityViewRepository<PostWithAuthorView, Long> {
}
