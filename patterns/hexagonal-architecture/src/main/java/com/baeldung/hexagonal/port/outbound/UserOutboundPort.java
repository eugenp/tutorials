package com.baeldung.hexagonal.port.outbound;

import com.baeldung.hexagonal.core.user.User;

public interface UserOutboundPort {

    void persistUser(User user);

    User retrieveUser(String email);

}
