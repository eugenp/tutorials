package com.baeldung.spring.data.persistence.findvsget.service;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalUserReferenceService {

    private static final Logger log = LoggerFactory.getLogger(TransactionalUserReferenceService.class);
    private JpaRepository<User, Long> repository;

    @Transactional
    public User findUserReference(final long id) {
        log.info("Before requesting a user");
        final User user = repository.getReferenceById(id);
        log.info("After requesting a user");
        return user;
    }

    @Transactional
    public User findAndUseUserReference(final long id) {
        final User user = repository.getReferenceById(id);
        log.info("Before accessing a username");
        final String firstName = user.getFirstName();
        log.info("After accessing a username: {}", firstName);
        return user;
    }

    public void setRepository(final JpaRepository<User, Long> repository) {
        this.repository = repository;
    }
}
