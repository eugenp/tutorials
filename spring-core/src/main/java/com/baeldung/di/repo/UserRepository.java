package com.baeldung.di.repo;

import com.baeldung.di.model.User;

/**
 * Created by DevTiwari on 23-01-17.
 */
public interface UserRepository {

    User getUserById(Long userId);

}
