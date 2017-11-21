package com.baeldung.dependencyinjectiontypes;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserDao {

    public List<String> getUsers() {
        return Arrays.asList("John", "Sarah", "Mike");
    }

}
