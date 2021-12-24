/*******************************************************************************
 *
 * Copyright 2017-2021, Bemindt Consulting Group, Inc.
 * 
 * UserRepositoryFactory.java
 * rich
 * Dec 23, 2021
 * 
 *******************************************************************************/

package com.baeldung.hexagonal.factory;

import com.baeldung.hexagonal.repository.FileBasedUserRespository;
import com.baeldung.hexagonal.repository.UserRepository;

public class UserRepositoryFactory {
    public static UserRepository getUserRepository() {
        // Here we would implement the logic to determine the type of 
        // UserRepository to return, presumably based on some configuration 
        // or environmental setting.
        return new FileBasedUserRespository();
    }
}
