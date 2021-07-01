package com.baeldung.architecture.hexagonal.user.core.ports.incoming;

import com.baeldung.architecture.hexagonal.user.core.model.RemoveUserCommand;

public interface RemoveUser {

    boolean handle(RemoveUserCommand command);
}
