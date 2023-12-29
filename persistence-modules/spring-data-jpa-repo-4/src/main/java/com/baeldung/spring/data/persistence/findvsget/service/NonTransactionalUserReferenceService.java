package com.baeldung.spring.data.persistence.findvsget.service;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import com.baeldung.spring.data.persistence.findvsget.repository.SimpleUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NonTransactionalUserReferenceService {

    private static final Logger log = LoggerFactory.getLogger(NonTransactionalUserReferenceService.class);
    private SimpleUserRepository repository;

    public User findUserReference(final long id) {
        log.info("Before requesting a user");
        final User user = repository.getReferenceById(id);
        log.info("After requesting a user");
        return user;
    }

    public User findAndUseUserReference(final long id) {
        final User user = repository.getReferenceById(id);
        log.info("Before accessing a username");
        final String firstName = user.getFirstName();
        log.info("This message shouldn't be displayed because of the thrown exception: {}", firstName);
        return user;
    }

    public void setRepository(final SimpleUserRepository repository) {
        this.repository = repository;
    }
}
