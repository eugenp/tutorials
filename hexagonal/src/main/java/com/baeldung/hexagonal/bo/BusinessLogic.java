package com.baeldung.hexagonal.bo;

import java.util.List;

import com.baeldung.hexagonal.dto.User;
import com.baeldung.hexagonal.factory.RepositoryFactory;
import com.baeldung.hexagonal.repository.UserRepository;

public class BusinessLogic {
    public void processUsers() {
        // Use factory to get an adapter for reading users
        UserRepository repo = RepositoryFactory.getUserRepository();
        // Get Users 
        List<User> userList = repo.getUsers();
        // Perform business logic
        userList.forEach(user -> {
            System.out.println(user.getLastName()+", "+user.getFirstName());
        });
    }
}
