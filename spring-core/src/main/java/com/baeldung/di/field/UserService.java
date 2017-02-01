package com.baeldung.di.field;

import com.baeldung.di.model.User;
import com.baeldung.di.repo.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by DevTiwari on 23-01-17.
 */
public class UserService {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    public User getUser(Long userId){
        User user = userRepositoryImpl.getUserById(userId);
        return user;
    }
}
