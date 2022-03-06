package com.baeldung.repository;

import com.baeldung.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository  implements  IUserRepository{
    @Override
    public User createUserRepo(User user) {
     //   create user impl will go here
        return user;
    }

    @Override
    public List<User> getUserRepo() {
        List<User> users= new ArrayList<User>();
        User user= new User();
        user.setName("Mark");
        user.setAge(5);
        users.add(user);
        return users;

    }
}
