package com.baeldung.roles.rolesauthorities.persistence;

import javax.transaction.Transactional;

import com.baeldung.roles.rolesauthorities.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    public User findUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}