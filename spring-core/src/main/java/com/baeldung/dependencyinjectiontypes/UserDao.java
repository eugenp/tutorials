package com.baeldung.dependencyinjectiontypes;

import java.util.Arrays;
import java.util.List;

public class UserDao {

    public List<String> getUsers() {
        return Arrays.asList("John", "Sarah", "Mike");
    }

}
