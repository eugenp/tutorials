package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.impl;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.exception.DocumentIDInvalidException;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.IUserService;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.DocumentIDEntity;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.IDocumentIdValidator;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.IUserRepositoryAdapter;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final IDocumentIdValidator documentIdValidator;

    @Override
    public User createNewUser(User user) throws DocumentIDInvalidException {
        DocumentIDEntity documentID = documentIdValidator.validateDocumentID(user.getDocumentID());
        if (!documentID.isValid()) {
            throw new DocumentIDInvalidException();
        }
        return User.of(user.getId(), user);
    }
}
