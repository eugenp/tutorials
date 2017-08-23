package org.baeldung.rolesauthorities.persistence;

import javax.transaction.Transactional;

import org.baeldung.rolesauthorities.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    // API
    @Override
    public User findUserByEmail(final String email) {
        return repository.findByEmail(email);
    }
}