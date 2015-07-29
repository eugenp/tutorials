package org.baeldung.repository;

import java.util.List;

import org.baeldung.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;

@Repository
public class UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertUser(User user) {
        mongoTemplate.insert(user, "user");
    }

    public List<User> listUser() {
        return mongoTemplate.findAll(User.class, "user");
    }

    public void removeUser(User user) {
        mongoTemplate.remove(user, "user");
    }

    public void saveUser(User user) {
        mongoTemplate.save(user, "user");
    }

    public User findAndModifyUser(String name, String newName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", newName);
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    public void updateFirstUser(String name, String newName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", newName);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public WriteResult upsertUser(String name, String newName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", newName);
        return mongoTemplate.upsert(query, update, User.class);
    }

    public void updateMultiUser(String name, String newName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", newName);
        mongoTemplate.updateMulti(query, update, User.class);
    }
}
