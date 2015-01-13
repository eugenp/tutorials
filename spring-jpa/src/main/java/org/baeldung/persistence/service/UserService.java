package org.baeldung.persistence.service;

import java.util.List;

import org.baeldung.persistence.dao.IUserDAO;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private IUserDAO dao;

    public UserService() {
        super();
    }

    public void saveUser(final User user) {
        dao.save(user);
    }

    public List<User> searchUser(final String first, final String last, final int minAge) {
        return dao.searchUser(first, last, minAge);
    }
}
