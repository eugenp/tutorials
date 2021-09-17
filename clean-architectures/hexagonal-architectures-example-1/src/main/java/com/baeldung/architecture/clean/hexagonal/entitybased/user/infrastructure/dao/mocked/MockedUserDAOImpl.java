package com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.dao.mocked;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.dao.IUserDAO;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
public class MockedUserDAOImpl implements IUserDAO {
    List<User> users;

    public MockedUserDAOImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User select(long userId) {
        return users.stream().filter(u -> userId == u.getId()).findFirst().orElse(null);
    }

    @Override
    public User upsert(User user) {
        User retUser = user;
        if (isNull(retUser.getId())) {
            retUser = User.of(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, retUser);
        } else {
            users.removeIf(u -> u.getId().equals(user.getId()));
        }

        users.add(retUser);

        return retUser;
    }

    @Override
    public void delete(User user) {
        users.removeIf(u -> u.getId().equals(user.getId()));
    }
}
