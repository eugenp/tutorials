/*******************************************************************************
 *
 * Copyright 2017-2021, Bemindt Consulting Group, Inc.
 * 
 * BusinessLogic.java
 * rich
 * Dec 23, 2021
 * 
 *******************************************************************************/

package com.baeldung.hexagonal.bo;

import java.util.List;

import com.baeldung.hexagonal.dto.User;
import com.baeldung.hexagonal.factory.UserRepositoryFactory;
import com.baeldung.hexagonal.repository.UserRepository;

public class BusinessLogic {
    public void processUsers() {
        // Use factory to get an adapter for reading users
        UserRepository repo = UserRepositoryFactory.getUserRepository();
        // Get Users 
        List<User> userList = repo.getUsers();
        // Perform business logic
    }
}
