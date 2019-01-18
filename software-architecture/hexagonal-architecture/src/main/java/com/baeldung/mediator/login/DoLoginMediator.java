package com.baeldung.mediator.login;

import com.baeldung.User;
import com.baeldung.mediator.Mediator;
import com.baeldung.mediator.login.contracts.LoginRequest;
import com.baeldung.mediator.login.contracts.LoginResponse;
import com.baeldung.port.UserInterfacePort;
import com.baeldung.port.UserPort;

public class DoLoginMediator implements Mediator<LoginRequest> {

    private UserPort userPort;

    public DoLoginMediator(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public void execute(LoginRequest req,
                        UserInterfacePort userInterfacePort) throws Exception {
        User user = userPort
                .findBy(req.getUsername(),
                        req.getPassword())
                .orElseThrow(()-> new IllegalStateException("user not found"));

        userInterfacePort.accept(new LoginResponse(user.getName()));
    }

    @Override
    public void discard() {

    }
}
