package com.baeldung.patterns.cqrs.aggregates;

import com.baeldung.patterns.cqrs.commands.CreateUserCommand;
import com.baeldung.patterns.cqrs.commands.UpdateUserCommand;
import com.baeldung.patterns.cqrs.repository.UserWriteRepository;
import com.baeldung.patterns.domain.User;

public class UserAggregate {

    private UserWriteRepository writeRepository;

    public UserAggregate(UserWriteRepository repository) {
        this.writeRepository = repository;
    }

    public User handleCreateUserCommand(CreateUserCommand command) {
        User user = new User(command.getUserId(), command.getFiratName(), command.getLastName());
        writeRepository.addUser(user.getUserid(), user);
        return user;
    }

    public User handleUpdateUserCommand(UpdateUserCommand command) {
        User user = writeRepository.getUser(command.getUserId());
        user.setAddresses(command.getAddresses());
        user.setContacts(command.getContacts());
        writeRepository.addUser(user.getUserid(), user);
        return user;
    }

}
