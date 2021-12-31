package com.baeldung.hexagonal.factory;

import com.baeldung.hexagonal.repository.FileBasedUserRepository;
import com.baeldung.hexagonal.repository.UserRepository;

public class RepositoryFactory {
    public static UserRepository getUserRepository() {
        // Here we would implement the logic to determine the type of 
        // UserRepository to return, presumably based on some configuration 
        // or environmental setting.
        return new FileBasedUserRepository();
    }
}
