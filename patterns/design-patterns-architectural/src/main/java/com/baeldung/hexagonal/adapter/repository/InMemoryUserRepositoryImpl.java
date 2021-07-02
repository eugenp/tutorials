package com.baeldung.hexagonal.adapter.repository;

import com.baeldung.hexagonal.domain.MemeberStatus;
import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.exception.UserNotFoundException;
import com.baeldung.hexagonal.repository.UserRepository;

import java.util.*;

public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Integer, User> dataStore = new HashMap<>();

    public User CreateUser(User user) {
        user.setUserId(1);
        user.setRewardedpoints(500);
        user.setStatus(MemeberStatus.BRONZE);

        dataStore.putIfAbsent(user.getUserId(), user);
        return user;
    }

    public User UpdateUser(User user) throws UserNotFoundException {

        if (!dataStore.containsKey(user.getUserId())) {
            throw new UserNotFoundException("User " + user.getUserId() + "can't be found");
        }
        dataStore.put(user.getUserId(), user);
        return user;

    }

    @Override
    public List<User> findAll() {
        List<User> all = new ArrayList<>();
        dataStore.values()
            .stream()
            .forEach(c -> all.add(c));
        return all;
    }

    @Override
    public Optional<User> findCustomerById(int customerId) {

        Optional<User> opt = Optional.empty();

        if (this.dataStore.containsKey(customerId)) {
            opt = Optional.of(dataStore.get(customerId));
        }
        return opt;
    }
}
