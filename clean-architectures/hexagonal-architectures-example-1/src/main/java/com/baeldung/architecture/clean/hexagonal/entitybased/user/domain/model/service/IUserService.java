package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.exception.DocumentIDInvalidException;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;

public interface IUserService {
    User createNewUser(User user) throws DocumentIDInvalidException;
}
