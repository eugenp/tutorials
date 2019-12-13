package com.baeldung.hexagonal.port.inbound;

import com.baeldung.hexagonal.core.user.User;

public interface UserInboundPort {

    void createUser(User user);

    User getUser(String email);

}
