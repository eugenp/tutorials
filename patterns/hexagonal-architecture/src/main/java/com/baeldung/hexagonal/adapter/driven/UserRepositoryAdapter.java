package com.baeldung.hexagonal.adapter.driven;

import com.baeldung.hexagonal.core.user.User;
import com.baeldung.hexagonal.port.outbound.UserOutboundPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryAdapter implements UserOutboundPort {

    private static Map<String, User> USER_MAP = new HashMap<>();

    @Override
    public void persistUser(User user) {
        USER_MAP.put(user.getEmail(), user);
    }

    @Override
    public User retrieveUser(String email) {
        return USER_MAP.get(email);
    }

}
