package org.baeldung.persistence.service.impl;

import java.util.Map;

import org.baeldung.persistence.dao.MyUserPredicatesBuilder;
import org.baeldung.persistence.dao.MyUserRepository;
import org.baeldung.persistence.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.expr.BooleanExpression;

@Service
@Transactional
public class MyUserService {

    @Autowired
    private MyUserRepository repository;

    public MyUserService() {
        super();
    }

    public Iterable<MyUser> search(final Map<String, Object> params) {
        final BooleanExpression predicate = MyUserPredicatesBuilder.buildUserPredicates(params);
        if (predicate == null)
            return repository.findAll();
        return repository.findAll(predicate);
    }

    public void save(final MyUser user) {
        repository.save(user);
    }
}

