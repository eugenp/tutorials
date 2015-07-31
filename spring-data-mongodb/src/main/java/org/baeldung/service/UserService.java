package org.baeldung.service;

import java.util.List;

import org.baeldung.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertUser(final User user) {
        mongoTemplate.insert(user, "user");
    }

    public List<User> listUser() {
        return mongoTemplate.findAll(User.class, "user");
    }

    public void removeUser(final User user) {
        mongoTemplate.remove(user, "user");
    }

    public void saveUser(final User user) {
        mongoTemplate.save(user, "user");
    }

    public User findAndModifyUser(final String name, final String newName) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        final Update update = new Update();
        update.set("name", newName);
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    public void updateFirstUser(final String name, final String newName) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        final Update update = new Update();
        update.set("name", newName);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public WriteResult upsertUser(final String name, final String newName) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        final Update update = new Update();
        update.set("name", newName);
        return mongoTemplate.upsert(query, update, User.class);
    }

    public void updateMultiUser(final String name, final String newName) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        final Update update = new Update();
        update.set("name", newName);
        mongoTemplate.updateMulti(query, update, User.class);
    }
}
