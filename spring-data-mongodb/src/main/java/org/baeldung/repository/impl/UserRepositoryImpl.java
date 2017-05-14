package org.baeldung.repository.impl;

import org.baeldung.model.User;
import org.baeldung.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public User findUserCustom(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id")
            .is(id));
        return mongoOps.findOne(query, User.class);
    }

}
