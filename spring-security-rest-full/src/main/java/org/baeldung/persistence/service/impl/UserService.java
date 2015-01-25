package org.baeldung.persistence.service.impl;

import java.util.List;

import org.baeldung.persistence.dao.IUserDAO;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.dao.UserSpecificationsBuilder;
import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private IUserDAO dao;

    @Autowired
    private UserRepository repository;

    public UserService() {
        super();
    }

    public void saveUser(final User user) {
        dao.save(user);
    }

    public List<User> searchUser(final List<SearchCriteria> params) {
        return dao.searchUser(params);
    }

    public List<User> searchBySpecification(final List<SearchCriteria> params) {
        final Specification<User> spec = UserSpecificationsBuilder.buildUserSpecs(params);
        if (spec == null)
            return repository.findAll();
        return repository.findAll(spec);
    }
}
