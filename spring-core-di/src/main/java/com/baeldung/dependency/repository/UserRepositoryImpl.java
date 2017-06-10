package com.baeldung.dependency.repository;

import com.baeldung.dependency.domain.User;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class UserRepositoryImpl implements IUserRepository {

    private HashMap<Integer, User> userHashMap;

    public UserRepositoryImpl() {
        userHashMap = new LinkedHashMap<>();
    }

    @Override
    public void save(final User user) {
        userHashMap.put(user.getId(), user);
    }

    @Override
    public User findById(final Integer id) {
        return userHashMap.get(id);
    }

    @Override
    public void deleteById(final Integer id) {
        userHashMap.remove(id);
    }

}
