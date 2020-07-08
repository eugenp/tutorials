package com.baeldung.hexgonalarchitecture.ports.implementation;

import com.baeldung.hexgonalarchitecture.entity.User;
import com.baeldung.hexgonalarchitecture.ports.definition.UserOutputPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserOutputPortImplementation implements UserOutputPort {

    private List<User> userList = new ArrayList<>();

    @Override
    public User save(User user) {
        userList.add(user);
        return user;
    }
}
