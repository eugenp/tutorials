package org.baeldung.persistence.katharsis;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.ResourceRepository;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserResourceRepository implements ResourceRepository<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(Long id, QueryParams params) {
        return userRepository.findOne(id);
    }

    @Override
    public Iterable<User> findAll(QueryParams params) {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> findAll(Iterable<Long> ids, QueryParams params) {
        return userRepository.findAll(ids);
    }

    @Override
    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
