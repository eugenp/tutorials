package com.baeldung.di.repo;

import com.baeldung.di.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by DevTiwari on 23-01-17.
 */

@Component
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User getUserById(Long userId) {
        return new User(userId);
    }
}
