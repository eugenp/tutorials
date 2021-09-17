package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.adapter.repository;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.adapter.repository.exceptions.UserDoesNotExistsException;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.dao.IUserDAO;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.IUserRepositoryAdapter;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRepositoryAdapter implements IUserRepositoryAdapter {
    private final IUserDAO userDAO;

    @Override
    public User get(long userId) {
        return userDAO.select(userId);
    }

    @Override
    public User save(User user) {
        return userDAO.upsert(user);
    }

    @Override
    public void remove(User user) throws UserRepositoryException {
        if (userDAO.select(user.getId()) != null) {
            userDAO.delete(user);
        } else {
            throw new UserDoesNotExistsException();
        }
    }
}
