package com.baeldung.hexagonal.core.user;

import com.baeldung.hexagonal.port.outbound.UserOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserOutboundPort userOutboundPort;

    @Override
    public void createUser(String email, String name) {
        userOutboundPort.persistUser(new User(email, name));
    }

    @Override
    public User getUser(String email) {
        return userOutboundPort.retrieveUser(email);
    }

}
