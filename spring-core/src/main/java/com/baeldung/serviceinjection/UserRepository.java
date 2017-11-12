package com.baeldung.serviceinjection;

import java.util.Collections;
import java.util.List;

public class UserRepository implements Repository<User> {

    @Override
    public List<User> findAll() {
        User bigBird = new User();
        bigBird.setId(1L);
        bigBird.setName("Big Bird");

        return Collections.singletonList(bigBird);
    }

}
