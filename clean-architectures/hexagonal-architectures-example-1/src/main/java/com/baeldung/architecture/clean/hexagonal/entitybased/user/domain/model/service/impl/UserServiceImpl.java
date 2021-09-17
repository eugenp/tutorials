package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.impl;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.exception.DocumentIDInvalidException;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.UserService;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.DocumentIDEntity;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.IDocumentIdValidatorAdapter;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.IUserRepositoryAdapter;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final IUserRepositoryAdapter repository;
    private final IDocumentIdValidatorAdapter documentIdValidatorService;

    @Override
    public User createNewUser(User user) throws DocumentIDInvalidException {
        DocumentIDEntity documentID = documentIdValidatorService.validateDocumentID(user.getDocumentID());
        if (!documentID.isValid()) {
            throw new DocumentIDInvalidException();
        }
        return User.of(user.getId(), user);
    }

    @Override
    public User get(User user) {
        return repository.get(user.getId());
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void remove(User user) throws UserRepositoryException {
        repository.remove(user);
    }
}
