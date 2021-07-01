package com.baeldung.architecture.hexagonal.user.core.ports.incoming;

import com.baeldung.architecture.hexagonal.user.core.model.AddUserCommand;

public interface AddUser {

    String handle(AddUserCommand command);
}
