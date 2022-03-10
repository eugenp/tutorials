package com.baeldung.hexagonalarchitecture.infrastructure.dao.couchbase;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalarchitecture.domain.dao.UserRepository;
import com.baeldung.hexagonalarchitecture.domain.model.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepoCouchbaseImpl implements UserRepository {

    private final UserRepositoryCouchbase repository;

    @Override
    public String save(User user) {
        return repository.save(new UserDoc(user))
            .getId();
    }

    @Override
    public User findById(String id) {
        return repository.findById(id)
            .map(UserDoc::toUser)
            .orElse(null);
    }
}
