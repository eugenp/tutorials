package com.baeldung.architecture.hexagonal.user.core;

import com.baeldung.architecture.hexagonal.user.core.model.AddUserCommand;
import com.baeldung.architecture.hexagonal.user.core.model.RemoveUserCommand;
import com.baeldung.architecture.hexagonal.user.core.model.User;
import com.baeldung.architecture.hexagonal.user.core.ports.incoming.AddUser;
import com.baeldung.architecture.hexagonal.user.core.ports.incoming.RemoveUser;
import com.baeldung.architecture.hexagonal.user.core.ports.outgoing.UserRepository;

import java.util.UUID;

public class UserFacade implements AddUser, RemoveUser {

    private final UserRepository repository;

    public UserFacade(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public String handle(AddUserCommand command) {
        String uniqueId = UUID.randomUUID().toString();
        User newUser = new User(uniqueId, command.getName(), command.getSurname());
        repository.save(newUser);
        return uniqueId;
    }

    @Override
    public boolean handle(RemoveUserCommand command) {
        return repository.delete(command.getUserId());
    }
}
