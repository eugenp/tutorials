package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.exception.DocumentIDInvalidException;

public interface IUserService {
    User createNewUser(User user) throws DocumentIDInvalidException;
}
