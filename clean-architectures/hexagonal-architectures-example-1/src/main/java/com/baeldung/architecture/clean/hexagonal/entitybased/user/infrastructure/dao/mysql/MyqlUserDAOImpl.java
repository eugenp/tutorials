package com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.dao.mysql;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.dao.IUserDAO;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;

//Implement proper DAO
public class MyqlUserDAOImpl implements IUserDAO {

    @Override
    public User select(long userId) {
        //TODO: implement select here
        return null;
    }

    @Override
    public User upsert(User user) {
        //TODO: implement upsert here
        return null;
    }

    @Override
    public void delete(User user) {
        //TODO: implement delete here
    }
}
